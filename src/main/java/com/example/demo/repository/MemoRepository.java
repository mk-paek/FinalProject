package com.example.demo.repository;

import com.example.demo.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    //CRUD
    // insert: save(엔티티)
    // select: findById(key), getOne(key)
    //update : save(엔티티)
    //delete : deleteById(key), delete(엔티티)

    // Paging & Sorting : findAll(Pageble pageable): PagingAndSortRepository

    // inline view : Oracle       Dialect
    // limit       : MySQL

    // Memo 객체에서 mno 값이 70~80 사이의 레코드를 mno 역순 으로 조회

    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);

    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);

    void deleteMemoByMnoLessThan(Long num);

    @Query("select m from Memo m order by m.mno desc")
    List<Memo> getListDesc();

    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :memoText where m.mno = :mno ")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

    /* ****************************************************************************** */

    // @Query와 페이징 처리
    // count를 처리하는 쿼리 적용
    @Query(value="select m from Memo where m.mno > :mno",
           countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);

    // Object[] 리턴 : 현재 필요한 데이터 만을 Object[] 형태로 선별적으로 추출 가능
    // (다른 데이터가 포함되어 적당한 엔티티 타입이 존재하지 않는 경우 유용하게 Object[] 을 리턴 타입으로 지정 가능
    // JOIN, GROUP BY
    @Query(value = "select m.mno, m.memoText, CURRENT_DATE from Memo m where m.mno > :mno",
            countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);

    //================================================================================
    @Query(value = "select * from memo where mno > 0", nativeQuery = true)
    List<Object[]> getNativeResult();

}
