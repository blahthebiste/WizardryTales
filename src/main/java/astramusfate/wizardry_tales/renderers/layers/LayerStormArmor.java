package astramusfate.wizardry_tales.renderers.layers;

import astramusfate.wizardry_tales.WizardryTales;
import astramusfate.wizardry_tales.api.Arcanist;
import astramusfate.wizardry_tales.registry.TalesEffects;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class LayerStormArmor extends LayerArmorBase<ModelBiped>
{
    private final RenderLivingBase<?> renderer;

    public LayerStormArmor(RenderLivingBase<?> rendererIn)
    {
        super(rendererIn);
        this.renderer = rendererIn;
    }

    protected void initArmor()
    {
        this.modelLeggings = new ModelBiped(0.5F);
        this.modelArmor = new ModelBiped(1.0F);
    }

    @SuppressWarnings("incomplete-switch")
    protected void setModelSlotVisible(@Nonnull ModelBiped model, EntityEquipmentSlot slotIn)
    {
        this.setModelVisible(model);

        switch (slotIn)
        {
            case HEAD:
                model.bipedHead.showModel = true;
                model.bipedHeadwear.showModel = true;
                break;
            case CHEST:
                model.bipedBody.showModel = true;
                model.bipedRightArm.showModel = true;
                model.bipedLeftArm.showModel = true;
                break;
            case LEGS:
                model.bipedBody.showModel = true;
                model.bipedRightLeg.showModel = true;
                model.bipedLeftLeg.showModel = true;
                break;
            case FEET:
                model.bipedRightLeg.showModel = true;
                model.bipedLeftLeg.showModel = true;
        }
    }

    public boolean isReady(EntityLivingBase living){
        return living.isPotionActive(TalesEffects.storm_armour);
    }

    public boolean isEmpowered(EntityLivingBase living){
        return living.isPotionActive(MobEffects.STRENGTH) || living.isPotionActive(MobEffects.RESISTANCE);
    }

    @Override
    public void doRenderLayer(@Nonnull EntityLivingBase living, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
       if(isReady(living)) {
           this.renderArmorLayer(living, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.CHEST);
           this.renderArmorLayer(living, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.LEGS);
           this.renderArmorLayer(living, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.FEET);
           this.renderArmorLayer(living, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.HEAD);
       }
    }

    protected void setModelVisible(ModelBiped model)
    {
        model.setVisible(false);
    }

    @Nonnull
    @Override
    protected ModelBiped getArmorModelHook(@Nonnull EntityLivingBase entity, ItemStack itemStack, EntityEquipmentSlot slot, ModelBiped model)
    {
        return model;
    }

    @Nonnull
    @Override
    public ResourceLocation getArmorResource(@Nonnull Entity entity,
                                             @Nonnull ItemStack stack, @Nonnull EntityEquipmentSlot slot, @Nonnull String type)
    {
        String string = "textures/layers/storm_king_armor.png";
        if(slot == EntityEquipmentSlot.LEGS){
            string = "textures/layers/storm_king_armor_legs.png";
        }

        return new ResourceLocation(WizardryTales.MODID, string);
    }

    private void renderArmorLayer(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn)
    {
        ModelBiped t = this.getModelFromSlot(slotIn);
        t.setModelAttributes(this.renderer.getMainModel());
        t.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
        this.setModelSlotVisible(t, slotIn);
        boolean flag = this.isLegSlot(slotIn);

        String string = "textures/layers/storm_king_armor.png";
        if(flag){
            string = "textures/layers/storm_king_armor_legs.png";
        }

        this.renderer.bindTexture(new ResourceLocation(WizardryTales.MODID, string));

        Arcanist.alpha(1.0f);
        t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        if (isEmpowered(entityLivingBaseIn))
        {
            renderEnchantedGlint(this.renderer, entityLivingBaseIn, t, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    private boolean isLegSlot(EntityEquipmentSlot slotIn)
    {
        return slotIn == EntityEquipmentSlot.LEGS;
    }
}