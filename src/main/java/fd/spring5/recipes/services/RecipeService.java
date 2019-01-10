package fd.spring5.recipes.services;

import fd.spring5.recipes.commands.RecipeCommand;
import fd.spring5.recipes.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    public Set<Recipe> getRecipes();
    public Recipe findById(Long id);
    public RecipeCommand findCommandById(Long id);
    public RecipeCommand saveRecipeCommand(RecipeCommand command);
    public void deleteById(Long id);

}
