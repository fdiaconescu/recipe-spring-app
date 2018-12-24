package fd.spring5.recipes.services;

import fd.spring5.recipes.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    public Set<Recipe> getRecipes();
}
