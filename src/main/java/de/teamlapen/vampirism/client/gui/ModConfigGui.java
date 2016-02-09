package de.teamlapen.vampirism.client.gui;

import de.teamlapen.lib.lib.config.BalanceValues;
import de.teamlapen.vampirism.config.Balance;
import de.teamlapen.vampirism.config.Configs;
import de.teamlapen.vampirism.util.REFERENCE;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Mod config gui
 */
public class ModConfigGui extends GuiConfig {
    public ModConfigGui(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), REFERENCE.MODID, true, true, REFERENCE.NAME, "Main Configuration");
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        list.addAll(new ConfigElement(Configs.getMainConfig().getCategory(Configs.CATEGORY_GENERAL)).getChildElements());
        list.add(new DummyConfigElement.DummyCategoryElement("village", "category.vampirism.village", new ConfigElement(Configs.getMainConfig().getCategory(Configs.CATEGORY_VILLAGE)).getChildElements()));
        list.add(new DummyConfigElement.DummyCategoryElement("disable", "category.vampirism.disable", new ConfigElement(Configs.getMainConfig().getCategory(Configs.CATEGORY_DISABLE)).getChildElements()));
        list.add(new DummyConfigElement.DummyCategoryElement("gui", "category.vampirism.gui", new ConfigElement(Configs.getMainConfig().getCategory(Configs.CATEGORY_GUI)).getChildElements()));
        list.add(new DummyConfigElement.DummyCategoryElement("balance", "category.vampirism.balance", BalanceEntry.class));
        return list;
    }

    public static class BalanceEntry extends GuiConfigEntries.CategoryEntry {

        public BalanceEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop) {
            super(owningScreen, owningEntryList, prop);
        }

        @SuppressWarnings("rawtypes")
        private static List<IConfigElement> getConfigElements() {
            List<IConfigElement> list = new ArrayList<IConfigElement>();
            Collection<BalanceValues> categories=Balance.getCategories().values();
            for(BalanceValues values:categories){
                list.add(createDummyElement(values));
            }

            return list;

        }

        private static DummyConfigElement.DummyCategoryElement createDummyElement(BalanceValues balance){
            return new DummyConfigElement.DummyCategoryElement("balance_"+balance.getName(),"category.vampirism.balance_"+balance.getName(),new ConfigElement(balance.getConfigCategory()).getChildElements());
        }

        @Override
        protected GuiScreen buildChildScreen() {
            return new GuiConfig(this.owningScreen, getConfigElements(), this.owningScreen.modID, Configs.CATEGORY_BALANCE, true, true, this.owningScreen.title,
                    ((this.owningScreen.titleLine2 == null ? "" : this.owningScreen.titleLine2) + " > " + this.name));
        }

    }
}
