package com.hongsheng;

import com.hongsheng.dto.DistributeQueryDto;
import com.hongsheng.entity.DistributeMethod;
import com.hongsheng.entity.DistributeStrategyDto;
import com.hongsheng.service.DistributeMethodService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoDBApplication.class)
public class DistriTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private DistributeMethodService distributeMethodService;

    @Test
    public void addDistributeTest(){
        DistributeStrategyDto distributeStrategyDto = new DistributeStrategyDto();
        distributeStrategyDto.setStoreId("27003");
        distributeStrategyDto.setPayAccountTypes(new int[]{2});
        DistributeStrategyDto.Automatic automatic = new DistributeStrategyDto.Automatic();
        DistributeStrategyDto.Manual manual = new DistributeStrategyDto.Manual();
        automatic.setDistributePlatforms(Arrays.asList("1","2","3").stream().mapToInt(Integer::parseInt).toArray());
        automatic.setOrderPlatforms(Arrays.asList("10","20","30").stream().mapToInt(Integer::parseInt).toArray());

        manual.setDistributePlatforms(Arrays.asList("4","5","6").stream().mapToInt(Integer::parseInt).toArray());

        distributeStrategyDto.setType(1);
        distributeStrategyDto.setContent(manual);

        Date date = new Date();
        distributeStrategyDto.setCreateTime(date);
        distributeStrategyDto.setUpdateTime(date);

        mongoTemplate.save(distributeStrategyDto);
    }

    /**
     * 查询mongo数据数量
     */
    @Test
    public void queryDistributeTest(){
        DistributeStrategyDto dto = new DistributeStrategyDto();
        Query query = getQuery(dto);
        long count = mongoTemplate.count(query, DistributeStrategyDto.class);
        System.out.println(count);
    }

    @Test
    public void queryAllStrategyList(){
        List<DistributeStrategyDto> all = mongoTemplate.findAll(DistributeStrategyDto.class);
        for (DistributeStrategyDto dto : all){
            DistributeMethod distributeMethod = new DistributeMethod();
            distributeMethod.setMerchantId(dto.getStoreId());
            Integer type = dto.getType();
            if (1 == type){
                DistributeStrategyDto.Manual manual = (DistributeStrategyDto.Manual)dto.getContent();
                // Arrays.asList(manual.getDistributePlatforms())
                distributeMethod.setDistributePlatforms(arrToStr(manual.getDistributePlatforms()));
            }else {
                DistributeStrategyDto.Automatic automatic = (DistributeStrategyDto.Automatic)dto.getContent();
                distributeMethod.setOrderPlatforms(arrToStr(automatic.getOrderPlatforms()));
                distributeMethod.setDistributePlatforms(arrToStr(automatic.getDistributePlatforms()));
            }
            distributeMethod.setCreateTime(dto.getCreateTime());
            distributeMethod.setUpdateTime(dto.getUpdateTime());
            distributeMethodService.save(distributeMethod);
        }
    }

    @Test
    public void testFindOne(){
        DistributeQueryDto dto = new DistributeQueryDto();
        dto.setStoreId("27003");
        dto.setPayAccountTypes(new int[]{1});

        Query query = getQuery(dto);
        DistributeStrategyDto distributeDto = mongoTemplate.findOne(query, DistributeStrategyDto.class);
        System.out.println(distributeDto.get_id());
    }

    private String arrToStr(int[] arr)
    {
        // 自定义一个字符缓冲区，
        StringBuilder sb = new StringBuilder();
        //遍历int数组，并将int数组中的元素转换成字符串储存到字符缓冲区中去
        for(int i=0; i<arr.length; i++)
        {
            if(i!=arr.length-1)
                sb.append(arr[i]+" ,");
            else
                sb.append(arr[i]);
        }
        return sb.toString();
    }

    public Query getQuery(Object queryObj) {
        Class c = queryObj.getClass();
        Field[] fields = c.getDeclaredFields();
        Query query = new Query();
        Criteria criteria = new Criteria();
        Field[] var6 = fields;
        int var7 = fields.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            Field field = var6[var8];
            String fieldName = field.getName();

            try {
                Method method = c.getMethod("get" + StringUtilsSon.firstLetterUpperCase(fieldName));
                Object fieldValue = method.invoke(queryObj);
                if (fieldValue != null) {
                    criteria.and(fieldName).is(fieldValue);
                }
            } catch (Exception var13) {
                var13.printStackTrace();
            }
        }

        query.addCriteria(criteria);
        return query;
    }

    public static class StringUtilsSon extends StringUtils {
        public StringUtilsSon() {
        }

        public static String firstLetterUpperCase(String str) {
            return str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
        }

        public static String firstLowerCase(String str) {
            return str.substring(0, 1).toLowerCase() + str.substring(1, str.length());
        }

        public static String markToHump(String str, String mark, Integer start) {
            String[] strs = str.split(mark);
            String returnStr = "";
            if (start == null) {
                start = 0;
            }

            for(int i = 0; i < strs.length; ++i) {
                returnStr = returnStr + (start == null ? firstLetterUpperCase(strs[i]) : (start == i ? strs[i] : firstLetterUpperCase(strs[i])));
            }

            return returnStr;
        }

        public static String addMarkToString(String str, String mark) {
            char[] chs = str.toCharArray();
            int start = 65;
            int end = 90;
            str = "";
            int i = -1;
            char[] var6 = chs;
            int var7 = chs.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                char ch = var6[var8];
                ++i;
                if (start <= ch && end >= ch) {
                    if (i != 0) {
                        str = str + mark + (ch + "").toLowerCase();
                    } else {
                        str = str + (ch + "").toLowerCase();
                    }
                } else {
                    str = str + ch;
                }
            }

            return str;
        }

        private static String replaceRange(String source, String oldChar, String newChar, String start, String end, int index) {
            int startInt = source.indexOf(start, index);
            int endInt = source.indexOf(end, index);
            return endInt == -1 ? source : replaceRange(source.substring(0, startInt) + source.substring(startInt, endInt).replace(oldChar, newChar) + source.substring(endInt, source.length()), oldChar, newChar, start, end, endInt + 1);
        }

        public static String replaceRange(String source, String oldChar, String newChar, String start, String end) {
            return replaceRange(source, oldChar, newChar, start, end, 0);
        }

        public static String getStringToNum(String str) {
            str = str.trim();
            String str2 = "";
            if (str != null && !"".equals(str)) {
                for(int i = 0; i < str.length(); ++i) {
                    if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                        str2 = str2 + str.charAt(i);
                    }
                }
            }

            return str2;
        }

        public static String getUnicode(String s) {
            try {
                StringBuffer out = new StringBuffer("");
                byte[] bytes = s.getBytes("unicode");

                for(int i = 0; i < bytes.length - 1; i += 2) {
                    out.append("\\u");
                    String str = Integer.toHexString(bytes[i + 1] & 255);

                    for(int j = str.length(); j < 2; ++j) {
                        out.append("0");
                    }

                    String str1 = Integer.toHexString(bytes[i] & 255);
                    out.append(str1);
                    out.append(str);
                }

                return out.toString();
            } catch (UnsupportedEncodingException var6) {
                var6.printStackTrace();
                return null;
            }
        }

        public static String unicodeToString(String str) {
            Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

            char ch;
            for(Matcher matcher = pattern.matcher(str); matcher.find(); str = str.replace(matcher.group(1), ch + "")) {
                ch = (char)Integer.parseInt(matcher.group(2), 16);
            }

            return str;
        }

        public static String sha256(String str) {
            return getHashStr(str, "SHA-256");
        }

        public static String getHashStr(String str, String type) {
            try {
                MessageDigest md = MessageDigest.getInstance(type);
                byte[] input = str.getBytes();
                byte[] buff = md.digest(input);
                return bytesToHex(buff);
            } catch (NoSuchAlgorithmException var5) {
                var5.printStackTrace();
                return null;
            }
        }

        public static String bytesToHex(byte[] bytes) {
            StringBuffer md5str = new StringBuffer();

            for(int i = 0; i < bytes.length; ++i) {
                int digital = bytes[i];
                if (digital < 0) {
                    digital += 256;
                }

                if (digital < 16) {
                    md5str.append("0");
                }

                md5str.append(Integer.toHexString(digital));
            }

            return md5str.toString().toUpperCase();
        }
        public static String passwordAddSalt(String password) {
            String salt = RandomStringUtils.randomAlphanumeric(15);
            return sha256(sha256(password) + salt) + salt;
        }

        public static boolean equalsSaltPassword(String inputPassword, String originalPasswordSha256AndSalt, Integer saltPosition) {
            if (saltPosition == null) {
                saltPosition = 64;
            }

            String salt = originalPasswordSha256AndSalt.substring(saltPosition, originalPasswordSha256AndSalt.length());
            String inputPasswordSha256 = sha256(sha256(inputPassword) + salt);
            String originalPasswrodSha256 = originalPasswordSha256AndSalt.substring(0, saltPosition);
            System.out.println("originalPasswrodSha256 = " + originalPasswrodSha256);
            return inputPasswordSha256.equals(originalPasswrodSha256);
        }

        public static boolean equalsSaltPassword(String inputPassword, String originalPasswordSha256AndSalt) {
            return equalsSaltPassword(inputPassword, originalPasswordSha256AndSalt, (Integer)null);
        }

        public static String initialCap(String str) {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }

        public static String supplementMark(String base, String mark, boolean isMarkFirst, Integer number) {
            number = number - base.length();

            for(int i = 0; i < number; ++i) {
                if (isMarkFirst) {
                    base = mark + base;
                } else {
                    base = base + mark;
                }
            }

            return base;
        }

        public static List<String> supplementMarkToList(String base, String mark, Integer lineLength) {
            ArrayList strList = new ArrayList();

            while(true) {
                int baseLength = base.length();
                if (baseLength <= lineLength) {
                    strList.add(supplementMark(base, mark, false, lineLength));
                    return strList;
                }

                String addStr = base.substring(0, lineLength);
                strList.add(addStr);
                base = base.substring(lineLength, baseLength - 1);
            }
        }

        public static boolean isChineseChar(char c) {
            return String.valueOf(c).matches("[一-龥]");
        }

        public static void main(String[] args) {
            int a = 3;
            System.out.println(a % 2);
        }
    }
}
