package com.tacm.tabooksapi.service.impl;

import com.tacm.tabooksapi.domain.entities.Categories;
import com.tacm.tabooksapi.domain.entities.NXBs;
import com.tacm.tabooksapi.exception.ApiException;
import com.tacm.tabooksapi.repository.NXBsRepository;
import com.tacm.tabooksapi.service.NXBsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class NXBsServiceImpl implements NXBsService {
    private NXBsRepository nxBsRepository;
    @Autowired
    public NXBsServiceImpl(NXBsRepository nxBsRepository) {
        this.nxBsRepository = nxBsRepository;
    }
    //    --------------------------- Get All NXB ----------------------------------  //
    @Override
    public List<NXBs> findAll() {
        return StreamSupport.stream(nxBsRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
    //    --------------------------- End Get All NXB ----------------------------------  //

    //    --------------------------- Search NXB By Name ----------------------------------  //
    @Override
    public List<NXBs> searchNxbByName(String keyword) {
        return StreamSupport.stream(nxBsRepository.findByNxbName(keyword).spliterator(), false)
                .collect(Collectors.toList());
    }
    //    --------------------------- End Search NXB By Name ----------------------------------  //

    //    ---------------------------------------- Add NXB  ----------------------------------  //

    @Override
    public NXBs addNXB(NXBs nxbs) {
        nxbs.setCreatedAt(LocalDateTime.now());
        return nxBsRepository.save(nxbs);
    }

    //    ------------------------------------ End Add NXB ----------------------------------  //

    //    ---------------------------------------- Update NXB  ----------------------------------  //

    @Override
    public NXBs updateNXB(NXBs nxbs, Long nxbId) throws ApiException {
        NXBs foundedNxb = findNxbById(nxbId);
        foundedNxb.setNxbName(nxbs.getNxbName());
        foundedNxb.setNxbInfo(nxbs.getNxbInfo());
        foundedNxb.setUpdatedAt(LocalDateTime.now());
        return nxBsRepository.save(foundedNxb);
    }

    //    ---------------------------------------- End Update NXB  ----------------------------------  //

    //    ---------------------------------------- Find NXB by ID  ----------------------------------  //
    @Override
    public NXBs findNxbById(Long nxbId) throws ApiException {
        Optional<NXBs> nxBs = nxBsRepository.findById(nxbId);
        if(nxBs.isPresent()) {
            return nxBs.get();
        }
        throw new ApiException("Không tìm thấy nxb với id + " + nxbId, HttpStatus.NOT_FOUND);
    }
    //    ---------------------------------------- End Find NXB by ID  ----------------------------------  //

    //    ---------------------------------------- Delete NXB by ID  ----------------------------------  //

    @Override
    public void deleteNXB(Long nxbId) {
        nxBsRepository.deleteById(nxbId);
    }

    //    ---------------------------------------- End Delete  NXB by ID  ----------------------------------  //

}
