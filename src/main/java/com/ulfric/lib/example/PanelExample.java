package com.ulfric.lib.example;

import com.ulfric.lib.bukkit.module.Plugin;

public class PanelExample extends Plugin {

    @Override
    public void onFirstEnable()
    {
        // To be fair I haven't tested this example,
        // but it *should* work. I think.
        this.addCommand(new PaneltestCommand(this));
    }

}
