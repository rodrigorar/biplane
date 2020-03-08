package pt.rodrigorar.caches;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.Duration;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import pt.rodrigorar.api.Cache;
import pt.rodrigorar.api.CacheConfiguration;
import pt.rodrigorar.utils.NotImplementedException;

public class TestSimpleCache {
    public static final String KEY_1 = "key-1";
    public static final String KEY_2 = "key-2";
    public static final String KEY_3 = "key-3";
    public static final String KEY_4 = "key-4";
    public static final String KEY_5 = "key-5";
    private static final String KEY_NULL = null;

    public static final String VALUE_1 = "value-1";
    private static final String VALUE_2 = "value-2";
    private static final String VALUE_3 = "value-3";
    private static final String VALUE_4 = "value-4";
    private static final String VALUE_5 = "value-5";
    private static final String VALUE_NULL = null;

    private CacheConfiguration configuration;

    @Before
    public void setUpClass() {
        this.configuration =
                new CacheConfiguration.Builder()
                        .setCacheType(Cache.Type.SIMPLE)
                        .setTimeToLive(Duration.ofMillis(10))
                        .build();
    }


    // put

    @Test
    public void testPutSuccess() {
        SimpleCache<String, String> underTest = new SimpleCache<>(this.configuration);
        String result = underTest.put(KEY_1, VALUE_1);

        assertNotNull(result);
        assertEquals(VALUE_1, result);

        Optional<String> entryResult = underTest.fetch(KEY_1);
        assertNotNull(entryResult);
        assertTrue(entryResult.isPresent());
        assertEquals(VALUE_1, entryResult.get());

    }

    @Test (expected = NullPointerException.class)
    public void testPutNullKey() {
        SimpleCache<String, String> underTest = new SimpleCache<>(this.configuration);
        underTest.put(KEY_NULL, VALUE_1);
    }

    @Test (expected = NullPointerException.class)
    public void testPutNullValue() {
        SimpleCache<String, String> underTest = new SimpleCache<>(this.configuration);
        underTest.put(KEY_1, VALUE_NULL);
    }

    @Test
    public void testExistingEntry() {
        SimpleCache<String, String> underTest = new SimpleCache<>(this.configuration);
        String result = underTest.put(KEY_1, VALUE_1);

        assertNotNull(result);
        assertEquals(VALUE_1, result);

        Optional<String> entryResult = underTest.fetch(KEY_1);
        assertNotNull(entryResult);
        assertTrue(entryResult.isPresent());
        assertEquals(VALUE_1, entryResult.get());

        String newResult = underTest.put(KEY_1, VALUE_2);
        assertNotNull(newResult);
        assertEquals(VALUE_2, newResult);

        Optional<String> entryNewResult = underTest.fetch(KEY_1);
        assertNotNull(entryNewResult);
        assertTrue(entryNewResult.isPresent());
        assertEquals(VALUE_2, entryNewResult.get());
    }

    // fetch

    @Test
    public void testFetchSuccess() {
        SimpleCache<String, String> underTest = new SimpleCache<>(configuration);
        underTest.put(KEY_1, VALUE_1);
        underTest.put(KEY_2, VALUE_2);
        underTest.put(KEY_3, VALUE_3);
        underTest.put(KEY_4, VALUE_4);
        underTest.put(KEY_5, VALUE_5);

        Optional<String> value2 = underTest.fetch(KEY_2);
        assertNotNull(value2);
        assertTrue(value2.isPresent());
        assertEquals(VALUE_2, value2.get());

        Optional<String> value4 = underTest.fetch(KEY_4);
        assertNotNull(value4);
        assertTrue(value4.isPresent());
        assertEquals(VALUE_4, value4.get());
    }

    @Test (expected = NullPointerException.class)
    public void testFetchNullKey() {
        SimpleCache<String, String> underTest = new SimpleCache<>(configuration);
        underTest.fetch(KEY_NULL);
    }

    @Test
    public void testFetchNullResult() {
        SimpleCache<String, String> underTest = new SimpleCache<>(configuration);
        Optional<String> result = underTest.fetch(KEY_4);
        assertNotNull(result);
        assertFalse(result.isPresent());
    }

    // invalidate

    @Test
    public void testInvalidateSuccess() {
        // TODO: Not implemented
    }

    @Test
    public void testInvalidateNoEntry() {
        // TODO: Not implemented
    }

    @Test
    public void testInvalidateNullKey() {
        // TODO: Not implemented
    }

    // evict

    @Test
    public void testEvictSuccess() {
        // TODO: Not implemented
    }

    @Test
    public void testEvictNullPolicy() {
        // TODO: Not implemented
    }
}
