package fd.spring5.recipes.services;

import fd.spring5.recipes.commands.IngredientCommand;
import fd.spring5.recipes.converters.IngredientToIngredientCommand;
import fd.spring5.recipes.domain.Recipe;
import fd.spring5.recipes.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand converter;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand converter, RecipeRepository recipeRepository) {
        this.converter = converter;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand getIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
        if (!recipeOpt.isPresent()) {
            log.error("recipe not found for id: " + recipeId);
        }
        Recipe recipe = recipeOpt.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> converter.convert(ingredient)).findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }

        return ingredientCommandOptional.get();
    }
}