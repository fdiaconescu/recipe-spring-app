package fd.spring5.recipes.services;

import fd.spring5.recipes.commands.IngredientCommand;
import fd.spring5.recipes.converters.IngredientCommandToIngredient;
import fd.spring5.recipes.converters.IngredientToIngredientCommand;
import fd.spring5.recipes.domain.Ingredient;
import fd.spring5.recipes.domain.Recipe;
import fd.spring5.recipes.repositories.RecipeRepository;
import fd.spring5.recipes.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToCommandConverter;
    private final IngredientCommandToIngredient commandToIngredientConverter;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository uomRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand igredientToCommandConverter,
                                 IngredientCommandToIngredient commandToIngredientConverter, RecipeRepository recipeRepository, UnitOfMeasureRepository uomRepository) {
        this.ingredientToCommandConverter = igredientToCommandConverter;
        this.commandToIngredientConverter = commandToIngredientConverter;
        this.recipeRepository = recipeRepository;
        this.uomRepository = uomRepository;
    }

    @Override
    public IngredientCommand getIngredientByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()){
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();

        if(!ingredientOptional.isPresent()){
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }

        Ingredient ingredient  = ingredientOptional.get();

        return ingredientToCommandConverter.convert(ingredient);
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!recipeOptional.isPresent()) {
            log.error("Recipe with id: " + ingredientCommand.getRecipeId() + " not found.");
            return new IngredientCommand();

        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUom(uomRepository
                        .findById(ingredientCommand.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                recipe.addIngredient(commandToIngredientConverter.convert(ingredientCommand));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            //to do check for fail
            return ingredientToCommandConverter.convert(savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst()
                    .get());
        }
    }

    @Override
    public void deleteByIds(Long recipeId, Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.error("Recipe with id: " + recipeId + " not found.");

        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(id))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientToDelete = ingredientOptional.get();
                ingredientToDelete.setRecipe(null);
                recipe.getIngredients().remove(ingredientOptional.get());
                recipeRepository.save(recipe);
            } else {
                log.error("Ingredient with id: " + id + " not found.");
            }

        }
    }
}