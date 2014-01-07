package org.digitalstain.datrie;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.digitalstain.datrie.store.IntegerArrayList;
import org.digitalstain.datrie.store.IntegerList;

/**
 * Created with IntelliJ IDEA.
 * User: davideuler
 * Date: 14-1-7
 * Time: 下午2:21
 * To change this template use File | Settings | File Templates.
 */
public class DictionaryTest extends TestCase
{
    /** CJK字符最大的UNICODE */
    private int MAX_CJK_UNICODE = 41000;

    /** CJK字符最小的UNICODE */
    private int MIN_CJK_UNICODE = 19968;

    private IntegerList toIntegerList(String word)
    {
        IntegerList list = new IntegerArrayList();

        for(char c : word.toCharArray())
        {
            list.add((int)(c-MIN_CJK_UNICODE));
        }
        return list;
    }

    public void testContain()
    {
        AbstractDoubleArrayTrie trie = new DoubleArrayTrieImpl(MAX_CJK_UNICODE-MIN_CJK_UNICODE+1);

        IntegerList word = toIntegerList("你好");
        trie.addToTrie(word);

        SearchResult searchResult = trie.containsPrefix(word);
        Assert.assertEquals(SearchResult.PERFECT_MATCH,searchResult);


        word = toIntegerList("川菜");
        trie.addToTrie(word);
        searchResult = trie.containsPrefix(word);
        Assert.assertEquals(SearchResult.PERFECT_MATCH,searchResult);

        word = toIntegerList("川菜馆");
        trie.addToTrie(word);
        searchResult = trie.containsPrefix(word);
        Assert.assertEquals(SearchResult.PERFECT_MATCH,searchResult);

        word = toIntegerList("川菜");
        searchResult = trie.containsPrefix(word);
        Assert.assertEquals(SearchResult.PURE_PREFIX,searchResult);



    }
}
