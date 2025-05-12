package org.bank.processing_center.service.factory;

import org.bank.processing_center.service.*;

/**
 * Factory for creating service instances
 */
public class ServiceFactory {

    private static ServiceFactory instance;

    private final CardService cardService;
    private final CardStatusService cardStatusService;
    private final PaymentSystemService paymentSystemService;
    private final AccountService accountService;
    private final AcquiringBankService acquiringBankService;
    private final MerchantCategoryCodeService merchantCategoryCodeService;
    private final TransactionTypeService transactionTypeService;
    private final ResponseCodeService responseCodeService;
    private final TerminalService terminalService;
    private final TransactionService transactionService;
    private final SalesPointService salesPointService;
    private final CurrencyService currencyService;
    private final IssuingBankService issuingBankService;

    private ServiceFactory(String daoType) {

        // Pass the daoType to the Service constructors
        accountService = new AccountService(daoType);
        acquiringBankService = new AcquiringBankService(daoType);
        cardService = new CardService(daoType);
        cardStatusService = new CardStatusService(daoType);
        currencyService = new CurrencyService(daoType);
        issuingBankService = new IssuingBankService(daoType);
        merchantCategoryCodeService = new MerchantCategoryCodeService(daoType);
        paymentSystemService = new PaymentSystemService(daoType);
        responseCodeService = new ResponseCodeService(daoType);
        salesPointService = new SalesPointService(daoType);
        terminalService = new TerminalService(daoType);
        transactionService = new TransactionService(daoType);
        transactionTypeService = new TransactionTypeService(daoType);

        // Create service instances

    }

    public static synchronized ServiceFactory getInstance(String daoType) {
        // Consider how you want to handle multiple instances with different daoTypes if needed
        if (instance == null) {
            instance = new ServiceFactory(daoType);
        }
        return instance;
    }

    public CardService getCardService() {
        return cardService;
    }

    public CardStatusService getCardStatusService() {
        return cardStatusService;
    }

    public PaymentSystemService getPaymentSystemService() {
        return paymentSystemService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public AcquiringBankService getAcquiringBankService() {
        return acquiringBankService;
    }

    public MerchantCategoryCodeService getMerchantCategoryCodeService() {
        return merchantCategoryCodeService;
    }

    public TransactionTypeService getTransactionTypeService() {
        return transactionTypeService;
    }

    public ResponseCodeService getResponseCodeService() {
        return responseCodeService;
    }

    public TerminalService getTerminalService() {
        return terminalService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public SalesPointService getSalesPointService() {
        return salesPointService;
    }

    public CurrencyService getCurrencyService() {
        return currencyService;
    }

    public IssuingBankService getIssuingBankService() {
        return issuingBankService;
    }
}
