package io.github.syst3ms.perfectlypacked.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class PerfectlyPackedModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parentScreen -> PerfectlyPackedConfig.HANDLER
                .instance()
                .makeConfigScreen(parentScreen);
    }
}
