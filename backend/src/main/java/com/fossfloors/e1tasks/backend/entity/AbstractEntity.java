package com.fossfloors.e1tasks.backend.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AbstractEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long   id;

  @Transient
  private Long   salt;

  @NotNull
  private long   dateCreated;

  @NotNull
  private long   dateModified;

  @NotNull
  @NotEmpty
  private String createdBy;

  @NotNull
  @NotEmpty
  private String modifiedBy;

  protected AbstractEntity() {
    // TODO find way to get logged in user
    createdBy = "user";
    modifiedBy = "user";

    long now = System.currentTimeMillis();
    dateCreated = now;
    dateModified = now;

    salt = now;
  }

  public Long getId() {
    return id;
  }

  public Long getSalt() {
    return salt;
  }

  public void setSalt(Long salt) {
    this.salt = salt;
  }

  public long getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(long dateCreated) {
    this.dateCreated = dateCreated;
  }

  public long getDateModified() {
    return dateModified;
  }

  public void setDateModified(long dateModified) {
    this.dateModified = dateModified;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public boolean isPersisted() {
    return id != null;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
    result = prime * result + (int) (dateCreated ^ (dateCreated >>> 32));
    result = prime * result + (int) (dateModified ^ (dateModified >>> 32));
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((modifiedBy == null) ? 0 : modifiedBy.hashCode());
    result = prime * result + ((salt == null) ? 0 : salt.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbstractEntity other = (AbstractEntity) obj;
    if (createdBy == null) {
      if (other.createdBy != null)
        return false;
    } else if (!createdBy.equals(other.createdBy))
      return false;
    if (dateCreated != other.dateCreated)
      return false;
    if (dateModified != other.dateModified)
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (modifiedBy == null) {
      if (other.modifiedBy != null)
        return false;
    } else if (!modifiedBy.equals(other.modifiedBy))
      return false;
    if (salt == null) {
      if (other.salt != null)
        return false;
    } else if (!salt.equals(other.salt))
      return false;
    return true;
  }

}