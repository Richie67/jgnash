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
package jgnash.engine.recurring;

import java.time.LocalDate;

import javax.persistence.Entity;

import jgnash.util.DateUtils;

/**
 * A daily reminder
 *
 * @author Craig Cavanaugh
 */
@Entity
public class DailyReminder extends Reminder {

    public DailyReminder() {
    }

    /**
     * @see jgnash.engine.recurring.Reminder#getIterator()
     */
    @Override
    public RecurringIterator getIterator() {
        return new DailyIterator();
    }

    /**
     * @return Return reminder type
     * @see jgnash.engine.recurring.Reminder#getReminderType()
     */
    @Override
    public ReminderType getReminderType() {
        return ReminderType.DAILY;
    }

    private class DailyIterator implements RecurringIterator {
        private LocalDate base;

        public DailyIterator() {
            if (getLastDate() != null) {
                base = getLastDate();
            } else {
                base = getStartDate().minusDays(getIncrement());
            }
        }

        /**
         * @see jgnash.engine.recurring.RecurringIterator#next()
         */
        @Override
        public LocalDate next() {
            if (isEnabled()) {
                base = base.plusDays(getIncrement());

                if (getEndDate() == null || DateUtils.before(base, getEndDate())) {
                    return base;
                }
            }
            return null;
        }
    }
}