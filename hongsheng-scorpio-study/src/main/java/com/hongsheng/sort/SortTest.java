package com.hongsheng.sort;

import org.assertj.core.util.Lists;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public class SortTest {

    /**
     * 从小到大
     * @param args
     *         partnerInfoDTOList = partnerInfoDTOList.stream()
     *         .sorted(Comparator.comparing(PartnerInfoDTO::getSortNum))
     *         .collect(Collectors.toList());
     */
    public static void main(String[] args) {
        SortDto sortDto1 = new SortDto();
        sortDto1.setName("萝卜");
        sortDto1.setPrice(20);
        SortDto sortDto2 = new SortDto();
        sortDto2.setName("土豆");
        sortDto2.setPrice(10);
        List<SortDto> sortDtos = Lists.newArrayList();
        sortDtos.add(sortDto1);
        sortDtos.add(sortDto2);
        sortDtos.sort(new Comparator<SortDto>() {
            @Override
            public int compare(SortDto o1, SortDto o2) {
                return new BigDecimal(o1.getPrice()).compareTo(new BigDecimal(o2.getPrice()));
            }
        });
        System.out.println(sortDtos);
    }
}
