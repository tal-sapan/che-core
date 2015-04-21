/*
 * CODENVY CONFIDENTIAL
 * __________________
 * 
 *  [2012] - [2015] Codenvy, S.A. 
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of Codenvy S.A. and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Codenvy S.A.
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Codenvy S.A..
 */
package org.eclipse.che.api.account.server.dao;

/**
 * Constraints for accounts search.
 *
 * @author Sergii Kabashniuk
 */
public class AccountSearchCriteria {
    private final String subscriptionPlanId;
    private final int    maxItems;
    private final int    skipCount;

    public AccountSearchCriteria(String subscriptionPlanId, int maxItems, int skipCount) {
        this.subscriptionPlanId = subscriptionPlanId;
        this.maxItems = maxItems;
        this.skipCount = skipCount;
    }

    public String getSubscriptionPlanId() {
        return subscriptionPlanId;
    }

    public int getMaxItems() {
        return maxItems;
    }

    public int getSkipCount() {
        return skipCount;
    }
}
