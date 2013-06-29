/*
 * jGnash, a personal finance application
 * Copyright (C) 2001-2013 Craig Cavanaugh
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jgnash.ui.register;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import jgnash.engine.EngineFactory;
import jgnash.engine.Transaction;
import jgnash.ui.StaticUIMethods;
import jgnash.ui.UIApplication;
import jgnash.ui.components.ExceptionDialog;
import jgnash.ui.components.ImageDialog;
import jgnash.ui.components.YesNoDialog;
import jgnash.util.FileUtils;
import jgnash.util.Resource;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Manages transaction attachments
 *
 * @author Craig Cavanaugh
 */
class AttachmentPanel extends JPanel implements ActionListener {

    private static final String LAST_DIR = "LastDir";

    private final Resource rb = Resource.get();

    File attachment = null;

    boolean moveAttachment = false;

    final JButton viewAttachmentButton;

    final JButton attachmentButton;

    final JButton deleteButton;

    AttachmentPanel() {
        attachmentButton = new JButton(Resource.getIcon("/jgnash/resource/mail-attachment.png"));
        deleteButton = new JButton(Resource.getIcon("/jgnash/resource/edit-delete.png"));
        viewAttachmentButton = new JButton(Resource.getIcon("/jgnash/resource/zoom-fit-best.png"));

        attachmentButton.setToolTipText(rb.getString("ToolTip.AddAttachment"));
        deleteButton.setToolTipText(rb.getString("ToolTip.DeleteAttachment"));
        viewAttachmentButton.setToolTipText(rb.getString("ToolTip.ViewAttachment"));

        FormLayout layout = new FormLayout("m, $rgap, m, $rgap, m", "f:d");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout, this);

        builder.append(attachmentButton, viewAttachmentButton, deleteButton);

        registerListeners();

        updateControlStates();
    }

    private void registerListeners() {
        attachmentButton.addActionListener(this);
        deleteButton.addActionListener(this);
        viewAttachmentButton.addActionListener(this);
    }

    Transaction modifyTransaction(final Transaction transaction) {
        // preserve any prior attachments
        if (transaction.getAttachment() != null && !transaction.getAttachment().isEmpty()) {
            final File baseFile = new File(EngineFactory.getActiveDatabase());

            attachment = FileUtils.resolve(baseFile, transaction.getAttachment());
        } else {
            attachment = null;
        }

        updateControlStates();

        return transaction;
    }

    void clear() {
        moveAttachment = false;
        attachment = null;

        updateControlStates();
    }

    /**
     * Chain builder for a new transaction
     *
     * @param transaction Transaction to work with
     * @return chained return of the passed transaction
     */
    Transaction buildTransaction(final Transaction transaction) {

        if (attachment != null) {
            if (moveAttachment) {   // move the attachment first
                moveAttachment();
            }

            final File baseFile = new File(EngineFactory.getActiveDatabase());
            transaction.setAttachment(FileUtils.relativize(baseFile, attachment).toString());
        } else {
            transaction.setAttachment(null);
        }

        updateControlStates();

        return transaction;
    }

    private void moveAttachment() {
        final File baseFile = new File(EngineFactory.getActiveDatabase());

        Path newPath = new File(FileUtils.getAttachmentDirectory(baseFile).toString() +
                File.separator + attachment.getName()).toPath();

        try {
            Files.move(attachment.toPath(), newPath, StandardCopyOption.ATOMIC_MOVE);
            attachment = newPath.toFile(); // update reference
        } catch (final IOException e) {
            Logger.getLogger(AttachmentPanel.class.getName()).log(Level.SEVERE, e.getLocalizedMessage(), e);
            new ExceptionDialog(UIApplication.getFrame(), e).setVisible(true);
        }
    }

    void attachmentAction() {
        final Preferences pref = Preferences.userNodeForPackage(AbstractBankTransactionPanel.class);
        final File baseFile = new File(EngineFactory.getActiveDatabase());

        final String[] fileSuffixes = ImageIO.getReaderFileSuffixes();

        StringBuilder description = new StringBuilder(rb.getString("Title.ImageFiles")).append(" (");

        for (int i = 0; i < fileSuffixes.length; i++) {
            description.append("*.");
            description.append(fileSuffixes[i]);
            if (i < fileSuffixes.length - 1) {
                description.append(", ");
            }
        }

        description.append(")");

        FileFilter fileFilter = new FileNameExtensionFilter(description.toString(), fileSuffixes);

        final JFileChooser chooser = new JFileChooser(pref.get(LAST_DIR, null));
        chooser.addChoosableFileFilter(fileFilter);
        chooser.setFileFilter(fileFilter);
        chooser.setMultiSelectionEnabled(false);
        chooser.setAcceptAllFileFilterUsed(false);

        if (attachment != null) {
            chooser.setSelectedFile(attachment);
        }

        if (chooser.showOpenDialog(UIApplication.getFrame()) == JFileChooser.APPROVE_OPTION) {
            pref.put(LAST_DIR, chooser.getCurrentDirectory().getAbsolutePath());

            File selectedFile = chooser.getSelectedFile();

            if (selectedFile != null) {

                boolean result = true;

                // TODO, add option to copy the file
                if (!FileUtils.getAttachmentDirectory(baseFile).toString().equals(selectedFile.getParent())) {

                    String message = MessageFormat.format(rb.getString("Message.WarnMoveFile"), selectedFile.toString(),
                            FileUtils.getAttachmentDirectory(baseFile).toString());

                    result = YesNoDialog.showYesNoDialog(UIApplication.getFrame(), new JLabel(message), rb.getString("Title.MoveFile"));

                    if (result) {
                        moveAttachment = true;

                        Path newPath = new File(FileUtils.getAttachmentDirectory(baseFile).toString() +
                                File.separator + selectedFile.getName()).toPath();

                        if (newPath.toFile().exists()) {
                            message = MessageFormat.format(rb.getString("Message.WarnSameFile"), selectedFile.toString(),
                                    FileUtils.getAttachmentDirectory(baseFile).toString());

                            StaticUIMethods.displayWarning(message);
                            moveAttachment = false;
                            result = false;
                        }
                    }
                }

                if (result) {
                    attachment = selectedFile;
                }
            }
        }
    }

    void showImageAction() {
        if (attachment != null) {
            ImageDialog.showImage(attachment);
        }
    }

    void updateControlStates() {
        attachmentButton.setEnabled(attachment == null);
        deleteButton.setEnabled(attachment != null);
        viewAttachmentButton.setEnabled(attachment != null);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == attachmentButton) {
            attachmentAction();
        } else if (e.getSource() == deleteButton) {
            attachment = null;

        } else if (e.getSource() == viewAttachmentButton) {
            showImageAction();
        }

        updateControlStates();
    }
}
