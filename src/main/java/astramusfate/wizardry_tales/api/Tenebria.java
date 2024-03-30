package astramusfate.wizardry_tales.api;

import electroblob.wizardry.util.EntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/** Special class to shorthand Attributes access and Entity manipulations **/
public class Tenebria {

    public static final IAttribute MAX_HEALTH = SharedMonsterAttributes.MAX_HEALTH;
    public static final IAttribute FOLLOW_RANGE = SharedMonsterAttributes.FOLLOW_RANGE;
    public static final IAttribute KNOCKBACK_RESISTANCE = SharedMonsterAttributes.KNOCKBACK_RESISTANCE;
    public static final IAttribute MOVEMENT_SPEED = SharedMonsterAttributes.MOVEMENT_SPEED;
    public static final IAttribute FLYING_SPEED = SharedMonsterAttributes.FLYING_SPEED;
    public static final IAttribute ATTACK_DAMAGE = SharedMonsterAttributes.ATTACK_DAMAGE;
    public static final IAttribute ATTACK_SPEED = SharedMonsterAttributes.ATTACK_SPEED;
    public static final IAttribute ARMOR = SharedMonsterAttributes.ARMOR;
    public static final IAttribute ARMOR_TOUGHNESS = SharedMonsterAttributes.ARMOR_TOUGHNESS;
    public static final IAttribute LUCK = SharedMonsterAttributes.LUCK;

    public static void create(World world, Entity entity){
        if (!world.isRemote) world.spawnEntity(entity);
    }

    public static void moveTowards(Entity target, Vec3d position, int divided){
        target.motionX = (position.x - target.posX) / divided;
        if (position.y != 0) target.motionY = (position.y - target.posX) / divided;
        target.motionZ = (position.z - target.posZ) / divided;
        target.velocityChanged=true;
    }
    public static void moveTowards(Entity target, Vec3d position){
        moveTowards(target, position, 6);
    }

    public static void moveTowards(Entity target, double x, double z){
        moveTowards(target, new Vec3d(x,0,z));
    }

    /** This method gets entity id from some creature. Applicable for some cases, like my Befriend spell. **/
    @Nullable
    public static EntityLivingBase getUniqueIdOwner(EntityLivingBase creature){
        EntityLivingBase entitylivingbase = null;
        if (creature.getEntityData().hasKey("friend")){
            entitylivingbase = (EntityLivingBase) EntityUtils.getEntityByUUID(creature.world,
                    creature.getEntityData().getUniqueId("friend"));

        }
        return entitylivingbase;
    }

    /** This method gets entity id from some creature. Applicable for some cases, like my Befriend spell. **/
    @Nullable
    public static EntityLivingBase getUniqueIdOwner(EntityLivingBase creature, String name){
        EntityLivingBase entitylivingbase = null;
        if (creature.getEntityData().hasUniqueId(name)) {
            entitylivingbase = (EntityLivingBase) EntityUtils.getEntityByUUID(creature.world,
                    creature.getEntityData().getUniqueId(name));
        }
        return entitylivingbase;
    }
}
