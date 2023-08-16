package com.example.demo.repository;

import com.example.demo.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


// 자동 완성: Ctrl + Alt + O
@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass() {
        System.out.println(memoRepository.getClass().getName());

    }

    @Test
    public void testInsertData() {     // Create
        IntStream.rangeClosed(1, 100).forEach( i -> {
            Memo memo = Memo.builder().memoText("Sample ... " + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {    // Read
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);   //
        System.out.println("===================================");

        if(result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Transactional
    @Test
    public void testSelect2() {
        Long mno = 100L;

        Memo memo = memoRepository.getOne(mno);
        System.out.println("===================================");
        System.out.println(memo);
    }

    @Test
    public void testUpdate() {    // update
        Memo memo = Memo.builder().mno(99L).memoText("Update text .... ").build();

        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void testDelete() {     // delete, EmptyResultDataAccessException
        Long mno = 99L;

        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault() {
        Pageable pageable = PageRequest.of(0, 10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("==============================================");

        System.out.println("Total pages: " + result.getTotalPages()); // 총 페이지 수
        System.out.println("Total count: " + result.getTotalElements()); // 전체 갯수
        System.out.println("Page number: " + result.getNumber());  //현재 페이지 번호
        System.out.println("Page size: " + result.getSize());  // 페이지 당 데이터 개수
        System.out.println("has next page?: " + result.hasNext());  // 다음 페이지 존재 여부
        System.out.println("first page? " + result.isFirst());  // 시작 페이지(0) 여부

        System.out.println("--------------------------------------------------");

        for(Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }

    @Test
    public void testSort() {
        Sort sort1 = Sort.by("mno").descending();   // order by mno desc;
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);


        Pageable pageable = PageRequest.of(0, 10, sortAll);  // 결합된 정렬 조건

        Page<Memo> result = memoRepository.findAll(pageable);

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testQueryMethods() {
       List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

       for(Memo memo: list) {
           System.out.println(memo);
       }
    }

    @Test
    public void testQueryMethodWithPageable() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);

        result.get().forEach(memo -> System.out.println(memo));
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteQueryMethods() {
        memoRepository.deleteMemoByMnoLessThan(10L);
    }
}
