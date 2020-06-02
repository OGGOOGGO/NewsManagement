package com.zzhujing.news.management.core.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 自定义分页对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "of")
@Accessors(chain = true)
@Builder
public class MyPage<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    @NonNull
    private long pageIndex;
    @NonNull
    private long pageSize;
    private long total;
    private List<T> data;


    public <V> MyPage<T> buildResultWithFunction(IPage<V> source, Function<V, T> convertFunc) {
        this.setTotal(source.getTotal());
        this.setData(source.getRecords().stream().map(convertFunc).collect(Collectors.toList()));
        return this;
    }

    public MyPage<T> buildResultIdentity(IPage<T> source) {
        this.setTotal(source.getTotal());
        this.setData(source.getRecords());
        return this;
    }

    @JsonIgnore
    public <T> Page<T> getQueryPage() {
        return new Page<>(this.getPageIndex(), this.getPageSize());
    }

}
