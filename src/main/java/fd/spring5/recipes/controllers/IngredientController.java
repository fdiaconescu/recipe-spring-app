package fd.spring5.recipes.controllers;

import fd.spring5.recipes.commands.IngredientCommand;
import fd.spring5.recipes.commands.RecipeCommand;
import fd.spring5.recipes.services.IngredientService;
import fd.spring5.recipes.services.RecipeService;
import fd.spring5.recipes.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService uomService;

    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService, UnitOfMeasureService uomService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.uomService = uomService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model){

        model.addAttribute("recipe", recipeService.findCommandById(new Long(recipeId)));

        return "recipe/ingredient/list";

    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable String recipeId,
                                 @PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient", ingredientService.getIngredientByRecipeIdAndIngredientId(new Long(recipeId),
                new Long(ingredientId)));

        return "recipe/ingredient/show";

    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId,
                                 @PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient", ingredientService.getIngredientByRecipeIdAndIngredientId(new Long(recipeId),
                new Long(ingredientId)));
        model.addAttribute("uomList", uomService.listAllUoms());

        return "recipe/ingredient/ingredientForm";

    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command){
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved receipe id:" + savedCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedCommand.getId());

        return "redirect:/recipe/" + savedCommand.getRecipeId()
                + "/ingredient/" + savedCommand.getId() + "/show";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{id}/delete")
    public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id){
        ingredientService.deleteByIds(new Long(recipeId), new Long(id));
        return  "redirect:/recipe/"+ recipeId + "/ingredients";
    }
}
