/*
 * Copyright (C) 2012 Intel Corporation
 * All rights reserved.
 */
package test.cmd;

import com.intel.mtwilson.setup.TextConsole;
import com.intel.mtwilson.setup.SetupException;
import org.junit.Test;
import com.intel.mtwilson.setup.cmd.*;

/**
 *
 * @author jbuhacoff
 */
public class TestSetupCommand {
    @Test
    public void testInitializeMysqlDatabase() throws Exception {
        InitDatabase cmd = new InitDatabase();
        cmd.execute(null);
    }
    
    @Test
    public void testCheckConfig() throws Exception {
//        TextConsole.main(new String[] { "CheckConfig" });
        TextConsole.main(new String[] { "CheckConfig", "--jpa" });
    }
    
    
    @Test
    public void testEraseUserAccounts() throws Exception {
//        TextConsole.main(new String[] { "CheckConfig" });
        TextConsole.main(new String[] { "EraseUserAccounts", "--all" });
    }
    


    @Test
    public void testExecuteCommand() throws Exception {
        TextConsole.main(new String[] { "SetMtWilsonURL" });
    }

}
