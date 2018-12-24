package fd.spring5.recipes.bootstrap;

import fd.spring5.recipes.domain.*;
import fd.spring5.recipes.repositories.CategoryRepository;
import fd.spring5.recipes.repositories.RecipeRepository;
import fd.spring5.recipes.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    CategoryRepository categoryRepo;
    UnitOfMeasureRepository ufmRepo;
    RecipeRepository recipeRepo;

    public RecipeBootstrap(CategoryRepository categoryRepo,
                           UnitOfMeasureRepository ufmRepo, RecipeRepository recipeRepo) {
        this.categoryRepo = categoryRepo;
        this.ufmRepo = ufmRepo;
        this.recipeRepo = recipeRepo;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepo.saveAll(getRecipes());
    }

    private List<Recipe> getRecipes () {
        List<Recipe> recipes = new ArrayList<Recipe>();

        Optional<UnitOfMeasure> tbsUnit = ufmRepo.findByDescription("Tablespoon");
        if(!tbsUnit.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> tspUnit = ufmRepo.findByDescription("Teaspoon");
        if(!tspUnit.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> eachUnit = ufmRepo.findByDescription("Each");
        if(!eachUnit.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> dashUnit = ufmRepo.findByDescription("Dash");
        if(!dashUnit.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> pintUnit = ufmRepo.findByDescription("Pint");
        if(!pintUnit.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }

        Optional<UnitOfMeasure> cupUnit = ufmRepo.findByDescription("Cup");
        if(!cupUnit.isPresent()) {
            throw new RuntimeException("Expected UOM not found");
        }

        UnitOfMeasure eachUom = eachUnit.get();
        UnitOfMeasure tbspUom = tbsUnit.get();
        UnitOfMeasure tspUom = tspUnit.get();
        UnitOfMeasure pintUom = pintUnit.get();
        UnitOfMeasure cupUom = cupUnit.get();
        UnitOfMeasure dashUom = dashUnit.get();

        Optional<Category> americanOpt = categoryRepo.findByDescription("American");
        if(!americanOpt.isPresent()) {
            throw new RuntimeException("Expected Category not found");
        }

        Optional<Category> mexicanOpt = categoryRepo.findByDescription("Mexican");
        if(!mexicanOpt.isPresent()) {
            throw new RuntimeException("Expected Category not found");
        }

        Category americanCateg = americanOpt.get();
        Category mexicanCateg = mexicanOpt.get();

        //Guac recipe
        Recipe guacRecipe = new Recipe();
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setCookTime(0);
        guacRecipe.setPrepTime(10);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. Place in a bowl."
         + "\n" + "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)"
          + "\n" + "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" + "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" + "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste."
                +"\n"+"4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "\n" + "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.");


        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("Read more at https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        guacNotes.setRecipe(guacRecipe);
        guacRecipe.setNotes(guacNotes);

        //Ingredients
        guacRecipe.addIngredient(new Ingredient("ripe avogado", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("kosher salt", new BigDecimal(0.5), tspUom));
        guacRecipe.addIngredient(new Ingredient("lemon juice", new BigDecimal(1), tbspUom));
        guacRecipe.addIngredient(new Ingredient("mince red onion", new BigDecimal(2), tbspUom));
        guacRecipe.addIngredient(new Ingredient("serrano chilles", new BigDecimal(2), eachUom));
        guacRecipe.addIngredient(new Ingredient("cilantro finely chopped", new BigDecimal(2), tbspUom));
        guacRecipe.addIngredient(new Ingredient("black pepper", new BigDecimal(1), dashUom));
        guacRecipe.addIngredient(new Ingredient("tomato", new BigDecimal(0.5), eachUom));

        guacRecipe.getCategories().add(americanCateg);
        guacRecipe.getCategories().add(mexicanCateg);

        recipes.add(guacRecipe);

        //Yummy Tacos
        Recipe tacosRecipe = new Recipe();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);

        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");

        tacoNotes.setRecipe(tacosRecipe);
        tacosRecipe.setNotes(tacoNotes);

        tacosRecipe.addIngredient(new Ingredient("Ancho Chili Powder", new BigDecimal(2), tbspUom));
        tacosRecipe.addIngredient(new Ingredient("Dried Oregano", new BigDecimal(1), tspUom));
        tacosRecipe.addIngredient(new Ingredient("Dried Cumin", new BigDecimal(1), tspUom));
        tacosRecipe.addIngredient(new Ingredient("Sugar", new BigDecimal(1), tspUom));
        tacosRecipe.addIngredient(new Ingredient("Salt", new BigDecimal(".5"), tspUom));
        tacosRecipe.addIngredient(new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUom));
        tacosRecipe.addIngredient(new Ingredient("finely grated orange zestr", new BigDecimal(1), tbspUom));
        tacosRecipe.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tbspUom));
        tacosRecipe.addIngredient(new Ingredient("Olive Oil", new BigDecimal(2), tbspUom));
        tacosRecipe.addIngredient(new Ingredient("boneless chicken thighs", new BigDecimal(4), tbspUom));
        tacosRecipe.addIngredient(new Ingredient("small corn tortillasr", new BigDecimal(8), eachUom));
        tacosRecipe.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cupUom));
        tacosRecipe.addIngredient(new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUom));
        tacosRecipe.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUom));
        tacosRecipe.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUom));
        tacosRecipe.addIngredient(new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUom));
        tacosRecipe.addIngredient(new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupUom));
        tacosRecipe.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUom));

        tacosRecipe.getCategories().add(americanCateg);
        tacosRecipe.getCategories().add(mexicanCateg);

        tacosRecipe.setUrl("http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        tacosRecipe.setServings(4);
        tacosRecipe.setSource("Simply Recipes");

        recipes.add(tacosRecipe);


        return recipes;
    }



}
