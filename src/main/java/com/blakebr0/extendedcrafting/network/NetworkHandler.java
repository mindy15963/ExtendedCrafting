package com.blakebr0.extendedcrafting.network;

import com.blakebr0.extendedcrafting.ExtendedCrafting;
import com.blakebr0.extendedcrafting.network.message.EjectModeSwitchMessage;
import com.blakebr0.extendedcrafting.network.message.InputLimitSwitchMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(ExtendedCrafting.MOD_ID, "main"), () -> "1.0", (s) -> true, (s) -> true);
	private static int id = 0;

	public static void onCommonSetup() {
		INSTANCE.registerMessage(id(), EjectModeSwitchMessage.class, EjectModeSwitchMessage::write, EjectModeSwitchMessage::read, EjectModeSwitchMessage::onMessage);
		INSTANCE.registerMessage(id(), InputLimitSwitchMessage.class, InputLimitSwitchMessage::write, InputLimitSwitchMessage::read, InputLimitSwitchMessage::onMessage);
	}

	private static int id() {
		return id++;
	}
}
