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

    public void testContainChinese()
    {
        AbstractDoubleArrayTrie trie = new DoubleArrayTrieImpl(MAX_CJK_UNICODE-MIN_CJK_UNICODE+1);

        IntegerList word = toIntegerList("你好");
        trie.addToTrie(word);

        SearchResult searchResult = trie.containsPrefix(word);
        Assert.assertEquals(SearchResult.PERFECT_MATCH,searchResult);


        word = toIntegerList("川菜馆");
        trie.addToTrie(word);
        searchResult = trie.containsPrefix(word);
        Assert.assertEquals(SearchResult.PERFECT_MATCH,searchResult);

        word = toIntegerList("川菜");
        searchResult = trie.containsPrefix(word);
        Assert.assertEquals(SearchResult.PURE_PREFIX,searchResult);



    }

    public void testAddWordWhosePrefixIsWord()
    {
        AbstractDoubleArrayTrie trie = new DoubleArrayTrieImpl(MAX_CJK_UNICODE-MIN_CJK_UNICODE+1);

        //当有多个词语共享前缀时，先添加最长的词语，后添加前缀词语：
        IntegerList word = toIntegerList("川菜馆");
        trie.addToTrie(word);
        SearchResult searchResult = trie.containsPrefix(word);
        Assert.assertEquals(SearchResult.PERFECT_MATCH,searchResult);

        word = toIntegerList("川菜");
        searchResult = trie.containsPrefix(word);
        Assert.assertEquals("词语前缀为词语",SearchResult.PURE_PREFIX,searchResult);

        //后加入,应为 prefix:
        word = toIntegerList("川菜");
        trie.addToTrie(word);
        searchResult = trie.containsPrefix(word);
        Assert.assertEquals("词语前缀应能识别出来",SearchResult.PREFIX,searchResult);

    }
}
