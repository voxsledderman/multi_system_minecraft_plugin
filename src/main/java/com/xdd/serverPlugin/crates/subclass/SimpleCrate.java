package com.xdd.serverPlugin.crates.subclass;

import com.xdd.serverPlugin.crates.Crate;
import com.xdd.serverPlugin.crates.CrateReward;

import java.util.List;

public class SimpleCrate extends Crate {

    public SimpleCrate(List<CrateReward> rewards) {
        super(rewards);
    }

    @Override
    public void open() {
        
    }
}
