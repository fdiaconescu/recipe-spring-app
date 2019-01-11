package fd.spring5.recipes.services;

import fd.spring5.recipes.commands.IngredientCommand;
import fd.spring5.recipes.domain.Ingredient;

public interface IngredientService {

    public IngredientCommand getIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
