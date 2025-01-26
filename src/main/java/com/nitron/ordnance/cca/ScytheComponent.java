package com.nitron.ordnance.cca;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class ScytheComponent implements AutoSyncedComponent, CommonTickingComponent {
    private boolean thrown = false;
    private final PlayerEntity player;

    public ScytheComponent(PlayerEntity player) {
        this.player = player;
    }

    public static ScytheComponent get (@NotNull PlayerEntity player){
        return (ScytheComponent) OrdnanceComponents.SCYTHE.get(player);
    }

    private void sync() { OrdnanceComponents.SCYTHE.sync(this.player); }

    @Override
    public void tick() {
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound) {
        this.thrown = nbtCompound.getBoolean("thrown");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound) {
        nbtCompound.putBoolean("thrown", this.thrown);
    }

    public boolean isThrowable() {
        return !thrown;
    }

    public void setThrown(boolean thrown) {
        this.thrown = thrown;
        this.sync();
    }
}
