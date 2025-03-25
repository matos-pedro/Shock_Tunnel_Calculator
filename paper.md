---
title: 'pridepy: A Python package to download and search data from PRIDE database'
tags:
  - Python
  - proteomics
  - mass spectrometry
  - pride archive
  - big data
authors:
  - name: Selvakumar Kamatchinathan
    orcid: 0009-0001-3644-2586
    affiliation: 1
  - name: Suresh Hewapathirana
    orcid: 0000-0002-7862-5022
    affiliation: 1
  - name: Chakradhar Bandla
    orcid: 0000-0001-6392-3759
    affiliation: 1
  - name: Santiago Insua
    orcid: 0000-0002-9247-243X
    affiliation: 1
  - name: Juan Antonio Vizcaíno
    orcid: 0000-0002-3905-4335
    affiliation: 1
  - name: Yasset Perez-Riverol
    orcid: 0000-0001-6579-6941
    affiliation: 1  
affiliations:
 - name: European Molecular Biology Laboratory, European Bioinformatics Institute (EMBL-EBI), Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
   index: 1
repository: https://github.com/PRIDE-Archive/pridepy   
date: 26 September 2024
bibliography: paper.bib
---
# Summary

The Proteomics Identification Database (PRIDE) [@Perez-Riverol2025-mo] is the world's largest repository for proteomics data and a founding member of ProteomeXchange [@Deutsch2023-mu]. Here, we introduce [`pridepy`](https://github.com/PRIDE-Archive/pridepy), a Python client designed to access PRIDE Archive data, including project metadata and file downloads. `pridepy` offers a flexible programmatic interface for searching, retrieving, and downloading data via the PRIDE REST API. This tool simplifies the integration of PRIDE datasets into bioinformatics pipelines, making it easier for researchers to handle large datasets programmatically.
