package fd.spring5.recipes.converters;

import fd.spring5.recipes.commands.CategoryCommand;
import fd.spring5.recipes.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {

    CategoryCommandToCategory converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    public void convert() {
        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(1L);
        categoryCommand.setDescription("Description");

        //when
        Category category = converter.convert(categoryCommand);

        //then
        assertEquals(new Long(1L), category.getId());
        assertEquals("Description", category.getDescription());

    }
}