/*
 * jGnash, a personal finance application
 * Copyright (C) 2001-2016 Craig Cavanaugh
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
package jgnash.resource.font;

import de.jensd.fx.glyphs.GlyphIcons;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import jgnash.uifx.skin.ThemeManager;
import jgnash.uifx.views.main.MainApplication;

/**
 * Simple implementation of a FontAwesome based icon.  This scales well with font size changes and is good for use
 * in table cells
 *
 * @author Craig Cavanaugh
 */
public class FontAwesomeLabel extends Label {

    private static final double DEFAULT_SIZE = 16.0;

    private final ObjectProperty<Object> glyphName = new SimpleObjectProperty<>();

    private final SimpleDoubleProperty size = new SimpleDoubleProperty(DEFAULT_SIZE);

    static {
        Font.loadFont(FontAwesomeLabel.class.getResource(FontAwesomeIconView.TTF_PATH).toExternalForm(),
                ThemeManager.getFontScaleProperty().get() * DEFAULT_SIZE);
    }

    @SuppressWarnings("unused")
    public FontAwesomeLabel() {
        this(FontAwesomeIcon.BUG);
    }

    public FontAwesomeLabel(final GlyphIcons glyphValue) {
        this(glyphValue, ThemeManager.getFontScaleProperty().get() * DEFAULT_SIZE, null);
    }

    public FontAwesomeLabel(final GlyphIcons glyphValue, final Double sizeValue) {
        this(glyphValue, sizeValue, null);
    }

    public FontAwesomeLabel(final GlyphIcons glyphValue, final Double sizeValue, Paint paint) {

        final StringExpression iconStyleProperty = Bindings.format("-fx-font-family: FontAwesome; -fx-font-size: %1$.6f;",
                ThemeManager.getFontScaleProperty().multiply(sizeValue));

        setGlyphName(glyphValue);
        size.setValue(sizeValue);
        styleProperty().bind(iconStyleProperty);

        if (paint != null) {
            setTextFill(paint);
        } else {
            textFillProperty().bind(ThemeManager.controlTextFillProperty());
        }

        getStylesheets().addAll(MainApplication.DEFAULT_CSS);
    }

    /**
     * Set the glyphName to display.
     *
     * @param value This can either be the Glyph Name or a unicode character representing the glyph.
     */
    @FXML
    public void setGlyphName(final Object value) {
        glyphName.set(value);

        if (value != null) {
            if (value instanceof Character) {
                setText(String.valueOf((char)value));
            } else {    //  GlyphIcons is assumed
                setText((String.valueOf(getGlyphChar(value.toString()))));
            }
        }
    }

    @FXML
    public Object getGlyphName() {
        return glyphName.get();
    }

    @FXML
    public void setSize(final Double value) {
        size.setValue(value);
    }

    @FXML
    public Double getSize() {
        return size.getValue();
    }

    private Character getGlyphChar(final String string) {
        return FontAwesomeIcon.valueOf(string).getChar();
    }
}
