package com.ssafy.backend.mokkoji.service;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.mokkoji.model.domain.MokkojiCategory;
import com.ssafy.backend.mokkoji.model.repository.MokkojiCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MokkojiCategoryServiceImpl implements MokkojiCategoryService{
    private final MokkojiCategoryRepository mokkojiCategoryRepository;



    @Override
    public void addMokkjiCategory(Mokkoji mokkoji, Category category) {
        mokkojiCategoryRepository.save(MokkojiCategory.builder()
                .category(category)
                .mokkoji(mokkoji)
                .build());
    }

    @Override
    public Map<Mokkoji, List<Category>> getMokkojis(Page<Mokkoji> mokkojiList) {
        List<Mokkoji> mokkojis = mokkojiList.getContent();
        List<MokkojiCategory> mokkojiCategories = mokkojiCategoryRepository.findByMokkojiIn(mokkojis);

        Map<Mokkoji, List<Category>> map = new HashMap<>();
        for (Mokkoji mokkoji :mokkojis) {
            if (!map.containsKey(mokkoji)) {
                map.put(mokkoji, new ArrayList<>());
            }
        }

        for (MokkojiCategory mokkojiCategory : mokkojiCategories) {
            Mokkoji mokkoji = mokkojiCategory.getMokkoji();
            Category category = mokkojiCategory.getCategory();

            map.get(mokkoji).add(category);
        }

        return map;
    }

    @Override
    public void deleteMokkojiCategory(Mokkoji mokkojiId) {
        mokkojiCategoryRepository.deleteAllByMokkoji(mokkojiId);
    }

    @Override
    public List<Category> getCategories(Mokkoji mokkoji) {
        List<Category> data = new ArrayList<>();
        List<MokkojiCategory> list = mokkojiCategoryRepository.findByMokkoji(mokkoji);
        for (MokkojiCategory m : list){
            data.add(m.getCategory());
        }
        return data;
    }
}
