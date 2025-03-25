---
title: 'STT_Calc, an Android app for shock tunnel characterization'
tags:
  - Shock Tunnel
  - Supersonics
authors:
  - name: Pedro Matos
    affiliation: 1
  - name: Leda Marise
    affiliation: 1
  - name: Dermeval Carinhanda
    affiliation: 1
affiliations:
 - name: Instituto de Estudos Avançados, DCTA, Brazil
   index: 1
repository: https://github.com/matos-pedro/Shock_Tunnel_Calculator
date: 25 March 2025
bibliography: paper.bib
---
# Summary

Shock tubes and shock tunnels are experimental facilities used to study high-speed gas dynamics, simulating extreme flow conditions relevant to aerospace, defense, and fundamental fluid mechanics research. Characterizing these facilities is a critical step in high-speed flow studies, yet traditional methods often require complex calculations and specialized software. This project introduces a mobile application that streamlines this process, providing a simplified, fast, user-friendly solution for researchers, students, and engineers. By enabling real-time characterization directly on a mobile device, the app enhances accessibility and accelerates experimental diagnostics. Its intuitive interface and instant results facilitate rapid decision-making, making it a valuable tool for both academic and applied research in aerodynamics.

# Statement of need

Shock Tube and Tunnel experiments, essential in the study of supersonic flows, require accurate calculations of shock wave conditions and free stream properties. Traditional methods for characterizing these experiments often rely on idealized assumptions based on the ideal gas law, which may not be suitable for real gas behavior in high-pressure, high-temperature conditions. These methods, while potentially fast, are limited in their ability to model real gas effects accurately and provide comprehensive results for more complex scenarios. Additionally, although some approaches exist that model real gas behavior, they typically rely on older computational codes, such as one from 1993[@minucci93], which are not intuitive and still require access to a computer, limiting their accessibility and ease of use in practical applications.

The Shock Tube and Tunnel Calculator addresses this issue by providing a mobile, user-friendly tool that enables rapid calculation of the necessary parameters for characterizing shock tube and tunnel experiments. The app focuses on real gas behavior, using equilibrium chemical transitions to estimate shock and reflected conditions based on the initial pressure, temperature, and shock wave speed. Additionally, when a nozzle is used, it estimates the free stream based on the throat-to-exit area ratio or measured Pitot pressure, assuming isentropic, non-reactive flow. This provides a more accurate solution for real gas scenarios, making it a practical tool for quick diagnostics and offering greater accessibility in various experimental settings.
