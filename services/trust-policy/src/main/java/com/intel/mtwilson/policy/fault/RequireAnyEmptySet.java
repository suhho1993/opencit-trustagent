/*
 * Copyright (C) 2012 Intel Corporation
 * All rights reserved.
 */
package com.intel.mtwilson.policy.fault;

import com.intel.mtwilson.policy.Fault;

/**
 * It is an error to declare a "RequireAny" policy with an EMPTY set,
 * because that allows a condition in which a host may be trusted
 * without meeting any policy at all.
 * 
 * @author jbuhacoff
 */
public class RequireAnyEmptySet extends Fault {
    public RequireAnyEmptySet() {
        super("RequireAny policy set must contain at least one policy");
    }
}
