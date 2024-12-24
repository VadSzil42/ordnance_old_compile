package com.nitron.ordnance.client.renderers;

import com.nitron.ordnance.common.entities.projectiles.BulletEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class BulletEntityRenderer extends ProjectileEntityRenderer<BulletEntity> {

    public static final Identifier TEXTURE = new Identifier("ordnance:textures/entity/projectiles/bullet.png");

    public BulletEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(BulletEntity entity) {
        return TEXTURE;
    }
}
