package com.example.nestco.dao.repository;

import com.example.nestco.models.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface MemberRepository extends JpaRepository <Member, String> {

    public Member findByNickname(String nickname);

    public Member findByUserId(String userId);

    public Member findByUsername(String username);

    public Member findByPhoneNumber(String phoneNumber);

    public Member findByProvider(String provider);

    public Member findByEmail(String Email);

    public List<Member> findAllByUsername(String username);

    public List<Member> findAllByProvider(String provider);

    @Override
    ArrayList<Member> findAll();

    @Query(value = "SELECT m FROM Member AS m WHERE m.username LIKE %:searchKey% OR m.email LIKE %:searchKey% OR m.userId LIKE %:searchKey% OR m.createDate LIKE %:searchKey%")
    Page<Member> findAllByQuery(Pageable pageable, @Param("searchKey") String searchKey);
}
