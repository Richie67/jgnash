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
package jgnash.engine.search;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import jgnash.engine.Transaction;

/**
 * SearchDefinition
 *
 * @author Craig Cavanaugh
 */
public class SearchDefinition {
   
    public enum Criteria {
        ALL,
        ANY
    }

    private final Criteria criteria;
    
    private final Collection<Matcher> matcherCollection;

    public SearchDefinition(final Criteria criteria, final Collection<Matcher> matcherCollection) {
        this.criteria = criteria;
        this.matcherCollection = matcherCollection;
    }

    boolean matches(final Transaction t) {
        boolean result = false;

        if (criteria == Criteria.ANY) {
            for (Matcher m : matcherCollection) {
                if (m.matches(t)) {
                    result = true;
                    break;
                }
            }
        } else { // must match all
            for (Matcher m : matcherCollection) {
                if (!m.matches(t)) {
                    break;
                }
            }
            result = true;  // made it past break, all matches were good
        }

        return result;
    }

    public Set<Transaction> matches(final Collection<Transaction> transactions) {
        return transactions.stream().filter(this::matches).collect(Collectors.toSet());
    }
}
