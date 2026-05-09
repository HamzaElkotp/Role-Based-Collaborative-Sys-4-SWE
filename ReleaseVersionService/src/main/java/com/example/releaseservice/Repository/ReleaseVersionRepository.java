package com.se2project.releaseservice.Repository;

import com.se2project.releaseservice.entity.ReleaseVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseVersionRepository extends JpaRepository<ReleaseVersion, Long> {
}