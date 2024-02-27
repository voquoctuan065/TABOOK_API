package com.tacm.tabooksapi.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tacm.tabooksapi.domain.entities.NXBs;
import com.tacm.tabooksapi.repository.NXBsRepository;
import com.tacm.tabooksapi.service.NXBsService;

@Service
public class NXBsServiceImpl implements NXBsService{
    @Autowired
    private NXBsRepository nxbsRepository;

    @Override
    public NXBs createNXBs(NXBs nxb) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        nxb.setCreated_at(currentDateTime);
        return nxbsRepository.save(nxb);
    }

    @Override
    public List<NXBs> findAll() {
        return StreamSupport.stream(nxbsRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
    }

	@Override
	public Optional<NXBs> findOne(Long id) {
		return nxbsRepository.findById(id);
	}

	@Override
	public NXBs partialUpdate(Long id, NXBs nxbs) {
		nxbs.setNxb_id(id);
        LocalDateTime updatedTime = LocalDateTime.now();

        return nxbsRepository.findById(id).map(
            existingNXBs -> {
                Optional.ofNullable(nxbs.getNxb_name()).ifPresent(existingNXBs::setNxb_name);
                Optional.ofNullable(nxbs.getNxb_info()).ifPresent(existingNXBs::setNxb_info);
                existingNXBs.setUpdated_at(updatedTime);
                return nxbsRepository.save(existingNXBs);
            }
        ).orElseThrow(() -> new RuntimeException("NXBs Not Found!"));
	}

	@Override
	public boolean isExist(Long id) {
		return nxbsRepository.existsById(id);
	}

	@Override
	public void delete(Long id) {
		nxbsRepository.deleteById(id);
	}  
}
