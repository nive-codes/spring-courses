package hello.hello_artifact.respository;

import hello.hello_artifact.domain.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate jdbcTemplate;

          /*생성자가 하나인 경우 Autowired 생략 가능*/
    public JdbcTemplateMemberRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public MemberVO save(MemberVO memberVO) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);   /*jtemplate 넘겨서 insert 생성*/
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String,Object> params = new HashMap<>();
        params.put("name", memberVO.getName());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params)); /*key를 받아서 생성*/
        memberVO.setId(key.longValue());
        return memberVO;

    }

    @Override
    public Optional<MemberVO> findById(Long id) {
        /*디자인 템플릿 패턴이라는 형태로 Jdbc에서 많이 소스가 간략화된 상태*/
        List<MemberVO> resultList =  jdbcTemplate.query("select * from member where id=?",memberVORowMapper(), id);
        return resultList.stream().findAny();   /*String으로 바꿔서 optional로 return */
    }

    @Override
    public Optional<MemberVO> findByUsername(String username) {
        /*쿼리를 날리면 rowMapper를 통해서 매핑을 하고 list로 받아서 optional로 변경 후 반환*/
        List<MemberVO> resultList =  jdbcTemplate.query("select * from member where name=?",memberVORowMapper(), username);
        return resultList.stream().findAny();   /*String으로 바꿔서 optional로 return */
    }

    @Override
    public List<MemberVO> findAll() {
        return  jdbcTemplate.query("select * from member",memberVORowMapper());

    }

    /*객체 생성에 대한 정의*/
    private RowMapper<MemberVO> memberVORowMapper(){
        return new RowMapper<MemberVO>() {
            @Override
            public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                MemberVO member = new MemberVO();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return member;
            }
        };

        /*람다 형태*/
        /*return (rs, rowNum) -> {
            MemberVO member = new MemberVO();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };*/
    }
}
