package owmii.powah.client.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import owmii.lib.util.Recipe;
import owmii.powah.Powah;
import owmii.powah.block.Blcks;
import owmii.powah.client.compat.jei.energizing.EnergizingCategory;
import owmii.powah.client.compat.jei.magmator.MagmatorCategory;
import owmii.powah.config.Configs;
import owmii.powah.item.Itms;
import owmii.powah.recipe.Recipes;

@JeiPlugin
public class PowahJEIPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new MagmatorCategory(helper));
        registration.addRecipeCategories(new CoolantCategory(helper));
        registration.addRecipeCategories(new SolidCoolantCategory(helper));
        registration.addRecipeCategories(new HeatSourceCategory(helper));
        registration.addRecipeCategories(new EnergizingCategory(helper));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Blcks.ENERGIZING_ORB), EnergizingCategory.ID);
        Blcks.ENERGIZING_ROD.getAll().forEach(block -> registration.addRecipeCatalyst(new ItemStack(block), EnergizingCategory.ID));
        Blcks.MAGMATOR.getAll().forEach(block -> registration.addRecipeCatalyst(new ItemStack(block), MagmatorCategory.ID));
        Blcks.THERMO_GENERATOR.getAll().forEach(block -> {
            registration.addRecipeCatalyst(new ItemStack(block), HeatSourceCategory.ID);
            registration.addRecipeCatalyst(new ItemStack(block), CoolantCategory.ID);
        });
        Blcks.REACTOR.getAll().forEach(block -> {
            registration.addRecipeCatalyst(new ItemStack(block), SolidCoolantCategory.ID);
            registration.addRecipeCatalyst(new ItemStack(block), CoolantCategory.ID);
        });
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(Recipe.getAll(Minecraft.getInstance().world, Recipes.ENERGIZING), EnergizingCategory.ID);
        registration.addRecipes(MagmatorCategory.Maker.getBucketRecipes(registration.getIngredientManager()), MagmatorCategory.ID);
        registration.addRecipes(CoolantCategory.Maker.getBucketRecipes(registration.getIngredientManager()), CoolantCategory.ID);
        registration.addRecipes(SolidCoolantCategory.Maker.getBucketRecipes(registration.getIngredientManager()), SolidCoolantCategory.ID);
        registration.addRecipes(HeatSourceCategory.Maker.getBucketRecipes(registration.getIngredientManager()), HeatSourceCategory.ID);

        if (Configs.GENERAL.player_aerial_pearl.get())
            registration.addIngredientInfo(new ItemStack(Itms.PLAYER_AERIAL_PEARL), VanillaTypes.ITEM, I18n.format("jei.powah.player_aerial_pearl"));
        if (Configs.GENERAL.binding_card_dim.get())
            registration.addIngredientInfo(new ItemStack(Itms.BINDING_CARD_DIM), VanillaTypes.ITEM, I18n.format("jei.powah.binding_card_dim"));
        if (Configs.GENERAL.lens_of_ender.get())
            registration.addIngredientInfo(new ItemStack(Itms.LENS_OF_ENDER), VanillaTypes.ITEM, I18n.format("jei.powah.lens_of_ender"));
    }


    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Powah.MOD_ID, "main");
    }
}
