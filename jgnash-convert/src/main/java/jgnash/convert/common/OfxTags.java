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
package jgnash.convert.common;

/**
 * Known OFX tags
 *
 * @author Craig Cavanaugh
 * @author Nicolas Bouillon
 */
public interface OfxTags {

    /**
     * Account ID
     */
    String ACCTID = "ACCTID";

    /**
     * Account Type
     */
    String ACCTTYPE = "ACCTTYPE";

    /**
     * Available balance
     */
    String AVAILBAL = "AVAILBAL";

    /**
     * Balance Amount
     */
    String BALAMT = "BALAMT";

    /**
     * Bank Account info
     */
    String BANKACCTFROM = "BANKACCTFROM";

    /**
     * Credit Card info
     */
    String CCACCTFROM = "CCACCTFROM";

    /**
     * Investment Account info
     */
    String INVACCTFROM = "INVACCTFROM";

    /**
     * Bank ID
     */
    String BANKID = "BANKID";

    /**
     * Stock purchase
     */
    String BUYSTOCK = "BUYSTOCK";

    String BUYTYPE = "BUYTYPE";

    /**
     * Branch identifier. May be required for some non-US banks
     */
    String BRANCHID = "BRANCHID";

    /**
     * Bank transaction list
     */
    String BANKTRANLIST = "BANKTRANLIST";

    String INVTRANLIST = "INVTRANLIST";

    String INVTRAN = "INVTRAN";

    String INVBUY = "INVBUY";

    String INVSELL = "INVSELL";

    String BROKERID = "BROKERID";

    /**
     * Check number
     */
    String CHECKNUM = "CHECKNUM";

    String CODE = "CODE";

    String CURDEF = "CURDEF";

    String CURRENCY = "CURRENCY";

    /**
     * Checking account type
     * @see #ACCTTYPE
     */
    String CHECKING = "CHECKING";

    /**
     * Credit line account type
     * @see #ACCTTYPE
     */
    String CREDITLINE = "CREDITLINE";

    /**
     * Money market account type
     * @see #ACCTTYPE
     */
    String MONEYMRKT = "MONEYMRKT";

    /**
     * Savings account type
     * @see #ACCTTYPE
     */
    String SAVINGS = "SAVINGS";

    /**
     * Credit transaction
     * @see #TRNTYPE
     */
    String CREDIT = "CREDIT";

    /**
     * Debit transaction
     * @see #TRNTYPE
     */
    String DEBIT = "DEBIT";

    /**
     * Date of balance
     * @see #LEDGERBAL
     * @see #AVAILBAL
     */
    String DTASOF = "DTASOF";

    /**
     * End date of transaction list
     */
    String DTEND = "DTEND";

    /**
     * Date posted
     */
    String DTPOSTED = "DTPOSTED";

    /**
     * Date user initiated transaction
     */
    String DTUSER = "DTUSER";

    String DTSERVER = "DTSERVER";

    /**
     * Start date of transaction list
     */
    String DTSTART = "DTSTART";

    String DTTRADE = "DTTRADE";

    String DTSETTLE = "DTSETTLE";

    String FI = "FI";

    String FID = "FID";

    /**
     * Financial Institution transaction id
     */
    String FITID = "FITID";


    String LANGUAGE = "LANGUAGE";

    /**
     * Account balance
     */
    String LEDGERBAL = "LEDGERBAL";

    /**
     * Transaction memo
     */
    String MEMO = "MEMO";

    String MESSAGE = "MESSAGE";

    /**
     * Name of payee or transaction description, may be used exclusive of {@code PAYEE}
     * @see #PAYEE
     */
    String NAME = "NAME";

    String OFX = "OFX";

    String ORG = "ORG";

    String ORIGCURRENCY = "ORIGCURRENCY";

    /**
     * Name of payee, may be used exclusive of {@code NAME}
     * @see #NAME
     */
    String PAYEE = "PAYEE";

    String PAYEEID = "PAYEEID";

    String REFNUM = "REFNUM";

    String SEVERITY = "SEVERITY";

    String SECID = "SECID";

    /**
     * Accounting SIC code
     */
    String SIC = "SIC";

    /**
     * Sign-on Message Set Aggregate
     */
    String SIGNONMSGSRSV1 = "SIGNONMSGSRSV1";

    String SONRS = "SONRS";

    String STATUS = "STATUS";

    /**
     * Bank statement response aggregate
     */
    String STMTRS = "STMTRS";

    /**
     * Investment account bank transaction
     */
    String INVBANKTRAN = "INVBANKTRAN";

    /**
     * Credit Card statement response aggregate
     */
    String CCSTMTRS = "CCSTMTRS";

    /**
     * Investment statement response aggregate
     */
    String INVSTMTRS = "INVSTMTRS";

    /**
     * Bank Transaction
     */
    String STMTTRN = "STMTTRN";

    String STMTTRNRS = "STMTTRNRS";

    String SUBACCTSEC = "SUBACCTSEC";

    String SUBACCTFUND = "SUBACCTFUND";

    String SELLSTOCK = "SELLSTOCK";

    String SELLTYPE = "SELLTYPE";

    String CCSTMTTRNRS = "CCSTMTTRNRS";

    String INVSTMTTRNRS = "INVSTMTTRNRS";

    String REINVEST = "REINVEST";

    String INCOME = "INCOME";

    String INCOMETYPE = "INCOMETYPE";

    /**
     * Transaction amount
     */
    String TRNAMT = "TRNAMT";

    /**
     * Transaction type
     */
    String TRNTYPE = "TRNTYPE";

    /**
     * Client Assigned Globally Unique Transaction ID
     */
    String TRNUID = "TRNUID";

    String TOTAL = "TOTAL";

    String USERKEY = "USERKEY";

    String UNIQUEID = "UNIQUEID";

    String UNIQUEIDTYPE = "UNIQUEIDTYPE";

    String UNITS = "UNITS";

    String UNITPRICE = "UNITPRICE";

    String COMMISSION = "COMMISSION";

    /**
     * Bank Message Set Aggregate
     */
    String BANKMSGSRSV1 = "BANKMSGSRSV1";

    String CREDITCARDMSGSRSV1 = "CREDITCARDMSGSRSV1";

    String INVSTMTMSGSRSV1 = "INVSTMTMSGSRSV1";

    /**
     * Intuit mucking up the OFX standard, Bank Id, In signon message
     */
    String INTUBID = "INTU.BID";

    /**
     * Intuit mucking up the OFX standard, User Id, In signon message
     */
    String INTUUSERID = "INTU.USERID";
}
