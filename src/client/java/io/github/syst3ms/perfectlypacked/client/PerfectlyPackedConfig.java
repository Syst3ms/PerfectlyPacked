package io.github.syst3ms.perfectlypacked.client;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class PerfectlyPackedConfig {
    public static final int DEFAULT_SLOT_SIZE = 24;
    public static final int DEFAULT_SLOT_PADDING = -3;

    public static ConfigClassHandler<PerfectlyPackedConfig> HANDLER = ConfigClassHandler.createBuilder(PerfectlyPackedConfig.class)
            .id(Identifier.fromNamespaceAndPath(PerfectlyPacked.MODID, "config"))
            .serializer(config ->
                    GsonConfigSerializerBuilder.create(config)
                            .setPath(FabricLoader.getInstance().getConfigDir().resolve("perfectlypacked.json5"))
                            .setJson5(true)
                            .build()
            )
            .build();

    public static PerfectlyPackedConfig getInstance() {
        return HANDLER.instance();
    }

    public enum TrivialPackingRendering implements NameableEnum {
        RECTANGULAR(Component.translatable("perfectlypacked.config.trivialPackingRendering.rectangular")),
        VANILLA(Component.translatable("perfectlypacked.config.trivialPackingRendering.vanilla"));

        private final Component name;

        TrivialPackingRendering(Component name) {
            this.name = name;
        }

        @Override
        public Component getDisplayName() {
            return name;
        }
    }

    @SerialEntry(comment = "Maximum of slots to show in vanilla bundle rendering")
    public int maxVanillaBundleSlotsToShow = 64;
    @SerialEntry(comment = "Max number of items for which custom rendering may be used")
    public int maxSpecialPackingSize = 64;
    @SerialEntry(comment = "Type of rendering to use when there is no special packing (can be RECTANGULAR or VANILLA)")
    public TrivialPackingRendering trivialPackingRendering = TrivialPackingRendering.RECTANGULAR;

    @SerialEntry(comment = "Size of the bundle slot sprites")
    public int slotSize = DEFAULT_SLOT_SIZE;
    @SerialEntry(comment = "Padding to use for bundle slot sprites. May be negative for oversized sprites")
    public int paddingSize = DEFAULT_SLOT_PADDING;

    public Screen makeConfigScreen(Screen parentScreen) {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("perfectlypacked.config.title", PerfectlyPacked.VERSION))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("perfectlypacked.config.category.title"))
                        .tooltip(Component.translatable("perfectlypacked.config.category.description"))
                        .group(this::makePackingOptionGroup)
                        .group(this::makeRenderOptionGroup)
                        .build()
                )
                .save(() -> HANDLER.save())
                .build()
                .generateScreen(parentScreen);
    }

    private OptionGroup makePackingOptionGroup() {
        return OptionGroup.createBuilder()
                .name(Component.translatable("perfectlypacked.config.group.packing"))
                .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("perfectlypacked.config.maxVanillaSlots"))
                        .description(OptionDescription.of(
                                Component.translatable("perfectlypacked.config.maxVanillaSlots.description_0"),
                                Component.translatable("perfectlypacked.config.maxVanillaSlots.description_1")
                        ))
                        .binding(12, () -> maxVanillaBundleSlotsToShow, newVal -> maxVanillaBundleSlotsToShow = newVal)
                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                .range(2, 99)
                                .step(1)
                        )
                        .build()
                )
                .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("perfectlypacked.config.maxCustomPackingSize"))
                        .description(OptionDescription.of(
                                Component.translatable("perfectlypacked.config.maxCustomPackingSize.description_0"),
                                Component.translatable("perfectlypacked.config.maxCustomPackingSize.description_1"),
                                Component.translatable("perfectlypacked.config.maxCustomPackingSize.description_2")
                        ))
                        .binding(64, () -> maxSpecialPackingSize, newVal -> maxSpecialPackingSize = newVal)
                        .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                .range(0, 99)
                                .step(1)
                                .formatValue(value -> value == 0 ? Component.literal("Always vanilla") : Component.literal(value.toString()))
                        )
                        .build()
                )
                .option(Option.<TrivialPackingRendering>createBuilder()
                        .name(Component.translatable("perfectlypacked.config.trivialPackingRendering"))
                        .description(OptionDescription.of(
                            Component.translatable("perfectlypacked.config.trivialPackingRendering.description_0"),
                            Component.translatable("perfectlypacked.config.trivialPackingRendering.description_1",
                                TrivialPackingRendering.RECTANGULAR.getDisplayName()
                                        .plainCopy()
                                        .withStyle(ChatFormatting.BOLD)
                            ),
                            Component.translatable("perfectlypacked.config.trivialPackingRendering.description_2",
                                TrivialPackingRendering.VANILLA.getDisplayName()
                                        .plainCopy()
                                        .withStyle(ChatFormatting.BOLD)
                            )
                        ))
                        .binding(
                                TrivialPackingRendering.RECTANGULAR,
                                () -> trivialPackingRendering,
                                rend -> trivialPackingRendering = rend
                        )
                        .controller(opt -> EnumControllerBuilder.create(opt)
                                .enumClass(TrivialPackingRendering.class)
                        )
                        .build()
                )
                .build();
    }

    private OptionGroup makeRenderOptionGroup() {
        return OptionGroup.createBuilder()
                .collapsed(true)
                .name(Component.translatable("perfectlypacked.config.group.rendering"))
                .description(OptionDescription.of(Component.translatable("perfectlypacked.config.group.rendering.description")))
                .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("perfectlypacked.config.slotSize"))
                        .description(OptionDescription.of(
                                Component.translatable("perfectlypacked.config.slotSize.description_0", DEFAULT_SLOT_SIZE),
                                Component.translatable(
                                        "perfectlypacked.config.slotSize.description_1",
                                        Component.literal("slot_background").withStyle(ChatFormatting.ITALIC),
                                        Component.literal("slot_highlight_back").withStyle(ChatFormatting.ITALIC),
                                        Component.literal("slot_highlight_front").withStyle(ChatFormatting.ITALIC)
                                )
                        ))
                        .binding(DEFAULT_SLOT_SIZE, () -> slotSize, newVal -> slotSize = newVal)
                        .controller(opt ->
                                IntegerFieldControllerBuilder.create(opt).range(1, 1024)
                        )
                        .build()
                )
                .option(Option.<Integer>createBuilder()
                        .name(Component.translatable("perfectlypacked.config.padding"))
                        .description(OptionDescription.of(
                                Component.translatable("perfectlypacked.config.padding.description_1"),
                                Component.translatable("perfectlypacked.config.padding.description_2"),
                                Component.translatable("perfectlypacked.config.padding.description_3")
                        ))
                        .binding(-2, () -> paddingSize, newVal -> paddingSize = newVal)
                        .controller(opt ->
                                IntegerFieldControllerBuilder.create(opt).range(-1024, 1024)
                        )
                        .build()
                )
                .build();
    }
}
