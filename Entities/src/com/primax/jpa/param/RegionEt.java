package com.primax.jpa.param;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.primax.jpa.base.EntityBase;

@Entity
@Table(name = "REGION_ET")
@Audited
public class RegionEt extends EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "sec_region_et", allocationSize = 1, initialValue = 1, sequenceName = "seq_region_et")
	@GeneratedValue(generator = "sec_region_et", strategy = GenerationType.SEQUENCE)
	@Column(name = "id_region")
	private Long idRegion;

	@Column(name = "descripcion", length = 100)
	private String descRegion;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "region")
	private List<ProvinciaEt> provincias;

	public Long getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(Long idRegion) {
		this.idRegion = idRegion;
	}

	public String getDescRegion() {
		return descRegion;
	}

	public void setDescRegion(String descRegion) {
		this.descRegion = descRegion;
	}

	public List<ProvinciaEt> getProvincias() {
		return provincias;
	}

	public void setProvincias(List<ProvinciaEt> provincias) {
		this.provincias = provincias;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RegionEt) {
			RegionEt other = (RegionEt) obj;
			if (this.idRegion == null)
				return this == other;
			return this.idRegion.equals(other.idRegion);
		}
		return false;
	}

}
