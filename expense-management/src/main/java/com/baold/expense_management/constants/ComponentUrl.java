package com.baold.expense_management.constants;

public class ComponentUrl {

    public interface Category {
        String CATEGORY_URL = "/api/category";
        String INCOME_URL = "/income";
        String EXPENSE_URL = "/expense";
        String CREATE_CATEGORY = "/create";
        String UPDATE_CATEGORY = "/update";
        String REMOVE_CATEGORY = "/remove";
    }

    public interface Transaction {
        String TRANSACTION_URL = "/api/transaction";
        String UPDATE_URL = "/update";
        String DELETE_URL = "/delete";
    }
}
