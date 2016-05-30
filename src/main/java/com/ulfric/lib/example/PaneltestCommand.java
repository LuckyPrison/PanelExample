package com.ulfric.lib.example;

import com.ulfric.lib.coffee.command.Command;
import com.ulfric.lib.craft.entity.player.Player;
import com.ulfric.lib.craft.inventory.item.ItemStack;
import com.ulfric.lib.craft.inventory.item.ItemUtils;
import com.ulfric.lib.craft.inventory.item.Material;
import com.ulfric.lib.craft.inventory.item.meta.ItemMeta;
import com.ulfric.lib.craft.panel.Button;
import com.ulfric.lib.craft.panel.Panel;
import com.ulfric.lib.craft.panel.anvil.AnvilPanel;
import com.ulfric.lib.craft.panel.anvil.AnvilSlot;
import com.ulfric.lib.craft.panel.page.PagePanel;
import com.ulfric.lib.craft.panel.standard.StandardPanel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class PaneltestCommand extends Command {

    public PaneltestCommand(PanelExample plugin) {
        super("paneltest", plugin);
    }

    @Override
    public void run() {
        // Panels only work on Players (obviously)
        if (!(super.getSender() instanceof Player))
        {
            super.getSender().sendMessage("&cPlease run as a Player!");
            return;
        }
        Player player = (Player) super.getSender();
        // Open an example StandardPanel
        openStandard(player);
    }

    private void openStandard(Player player)
    {
        // Create a new StandardPanel with size 27, title "Title"
        StandardPanel panel = Panel.createStandard(27, "Title");

        // Make an Arrow ItemStack
        ItemStack arrow = ItemUtils.getItem(Material.of("ARROW"));

        // Amount
        arrow.setAmount(4);

        // Meta
        ItemMeta meta = arrow.getMeta();

        // Display name
        meta.setDisplayName("I am 4 arrows");

        // Lore
        meta.setPluginLore(Collections.singletonList("My name is Lore"));

        // Re-Set Meta
        arrow.setMeta(meta);

        // Set slot 13 (centre) to the arrow ItemStack
        panel.setItem(13, arrow);

        // Create a new Button with Button.builder()
        Button button = Button.builder()
                // Button is for slot 13, ItemStack arrow
                .addSlot(13, arrow)
                // Add an action, i.e. InventoryClickEvent Listener
                .addAction(event ->
                {
                    // When a player clicks the arrow, open the PagePanel example
                    openPaged(player);
                })
                // Build to return button instance
                .build();

        // Add Button to StandardPanel
        panel.addButton(button);

        // Open to player
        panel.open(player);
    }

    private void openPaged(Player player)
    {
        // Create a new PagePanel with title "Another title!"
        PagePanel panel = Panel.createPaged("Another title!");

        // Initialize ArrayList to store items
        List<ItemStack> items = new ArrayList<>();

        // 250 iterations
        IntStream.range(0, 250).forEach(i ->
        {
            // Create new Dirt ItemStack
            ItemStack item = ItemUtils.getItem(Material.of("DIRT"));
            // Set amount to current iteration
            item.setAmount(i);
            // Add to items List
            items.add(item);
        });

        // Add all items to panel
        panel.withItems(items);

        // Add an EventConsumer i.e. InventoryClickEvent Listener
        panel.withEventConsumer(event ->
        {
            // Open Anvil example on click
            openAnvil(player);
        });

        // Open panel to player
        panel.open(player);
    }

    private void openAnvil(Player player)
    {
        // Create a new AnvilPanel instance
        AnvilPanel panel = Panel.createAnvil();

        // Create new Diamond ItemStack
        ItemStack input = ItemUtils.getItem(Material.of("DIAMOND"));

        // Get ItemMeta
        ItemMeta meta = input.getMeta();

        // Display name will be the default text in Anvil
        meta.setDisplayName("Default text");

        // Set the meta
        input.setMeta(meta);

        // Set item to the INPUT_LEFT slot
        panel.setItem(AnvilSlot.INPUT_LEFT, input);

        // Set the ClickConsumer (AnvilClickEvent)
        panel.setClickConsumer(click ->
        {
            // If player clicks OUTPUT slot
            if (click.getSlot() == AnvilSlot.OUTPUT)
            {
                // Send message with text they input
                player.sendMessage("&aText: " + click.getText());
            }
        });

        // Open panel to player
        panel.open(player);
    }


}
