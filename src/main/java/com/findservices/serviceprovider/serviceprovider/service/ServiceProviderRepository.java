package com.findservices.serviceprovider.serviceprovider.service;

import com.findservices.serviceprovider.serviceprovider.model.CategoryType;
import com.findservices.serviceprovider.serviceprovider.model.ServiceProviderEntity;
import com.findservices.serviceprovider.serviceprovider.model.ServiceProviderListTuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceProviderRepository extends JpaRepository<ServiceProviderEntity, UUID> {

    // language = sql
    @Query("SELECT DISTINCT " +
            "sp.description as descrioption, " +
            "sp.id  as id, " +
            "u.name as name, " +
            "u.lastName as lastName, " +
            "sp.category as category, " +
            "u.userPhotoUrl as photo " +
            "FROM ServiceProviderEntity sp " +
            "LEFT JOIN sp.user u on u.id = sp.id " +
            "left join sp.actuationCities c " +
            "where (upper(u.name) LIKE :name OR upper(u.lastName) LIKE :name) " +
            "AND sp.category = :category AND c.name = :city")
    List<ServiceProviderListTuple> findByUserNameLikeIgnoreCaseOrUserLastNameLikeIgnoreCaseAndCategoryAndActuationCitiesName(
            @Param("name") String name, @Param("category") CategoryType category, @Param("city") String city);

    @Query("SELECT DISTINCT " +
            "sp.description as descrioption, " +
            "sp.id  as id, " +
            "u.name as name, " +
            "u.lastName as lastName, " +
            "sp.category as category, " +
            "u.userPhotoUrl as photo " +
            "FROM ServiceProviderEntity sp " +
            "LEFT JOIN sp.user u on u.id = sp.id " +
            "where (upper(u.name) LIKE :name OR upper(u.lastName) LIKE :name) " +
            "AND sp.category = :category")
    List<ServiceProviderListTuple> findByUserNameLikeIgnoreCaseOrUserLastNameLikeIgnoreCaseAndCategory(
            @Param("name") String name, @Param("category") CategoryType category);

    @Query("SELECT DISTINCT " +
            "sp.description as descrioption, " +
            "sp.id  as id, " +
            "u.name as name, " +
            "u.lastName as lastName, " +
            "sp.category as category, " +
            "u.userPhotoUrl as photo " +
            "FROM ServiceProviderEntity sp " +
            "LEFT JOIN sp.user u on u.id = sp.id " +
            "left join sp.actuationCities c " +
            "where (upper(u.name) LIKE :name OR upper(u.lastName) LIKE :name) " +
            "AND upper(c.name) = upper(:city)")
    List<ServiceProviderListTuple> findByUserNameLikeIgnoreCaseOrUserLastNameLikeIgnoreCaseAndActuationCitiesName(
            @Param("name") String name, @Param("city") String city);

    @Query("SELECT DISTINCT " +
            "sp.description as descrioption, " +
            "sp.id  as id, " +
            "u.name as name, " +
            "u.lastName as lastName, " +
            "sp.category as category, " +
            "u.userPhotoUrl as photo " +
            "FROM ServiceProviderEntity sp " +
            "LEFT JOIN sp.user u on u.id = sp.id " +
            "left join sp.actuationCities c " +
            "where upper(u.name) LIKE :name OR upper(u.lastName) LIKE :name")
    List<ServiceProviderListTuple> findByUserNameLikeIgnoreCaseOrUserLastNameLikeIgnoreCase(@Param("name") String name);
}
