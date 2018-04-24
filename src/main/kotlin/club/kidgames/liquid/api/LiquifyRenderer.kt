package club.kidgames.liquid.api

import club.kidgames.liquid.liqp.ModelContributor
import liqp.nodes.RenderContext
import org.bukkit.entity.Player
import java.util.function.Consumer

/**
 * Liquid text merging plugin.  This plugin uses liquid templating language to allow for robust rending capabilities.
 */
interface LiquifyRenderer {

  fun execute(template: String, player: Player): Any? {
    return this.execute(template, { model: LiquidModelMap -> model.player = player })
  }

  fun render(template: String, player: Player): String {
    return render(template, { model -> model.player = player })
  }

  fun execute(template: String, modelContributor: List<Consumer<LiquidModelMap>>): Any? {
    return this.execute(template, *modelContributor
        .map { { model: LiquidModelMap -> it.accept(model) } }
        .toTypedArray())
  }

  fun render(template: String, modelContributor: List<Consumer<LiquidModelMap>>): String {
    return this.render(template, *modelContributor
        .map { { model: LiquidModelMap -> it.accept(model) } }
        .toTypedArray())
  }

  fun newRenderContext(vararg modelContributors: ModelContributor): RenderContext

  fun execute(template: String, vararg modelContributor: ModelContributor): Any?

  fun render(template: String, vararg modelContributor: ModelContributor): String

  fun render(template: String, context: RenderContext): String
}