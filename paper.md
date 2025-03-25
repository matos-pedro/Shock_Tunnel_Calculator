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

Shock Tube and Tunnel experiments, essential in the study of supersonic flows, require accurate calculations of shock wave conditions and free stream properties. Traditional methods for characterizing these experiments often rely on idealized assumptions based on the ideal gas law, which may not be suitable for real gas behavior in high-pressure, high-temperature conditions. These methods, while potentially fast, are limited in their ability to model real gas effects accurately and provide comprehensive results for more complex scenarios. Additionally, although some approaches exist that model real gas behavior, they typically rely on older computational codes, such as one from 1993[@minucci93], which are not usually intuitive and still require access to a computer, limiting their accessibility and ease of use in practical applications.

The Shock Tube and Tunnel Calculator addresses this issue by providing a mobile, user-friendly tool for rapid characterization of shock tube and tunnel experiments. The app estimates shocked and reflected conditions based on initial gas properties and shock wave speed, incorporating real air equilibrium effects. It also computes free-stream parameters using either the throat-to-exit area ratio or measured Pitot pressure, assuming isentropic, non-reactive flow. This enables accurate real gas modeling while ensuring accessibility in experimental settings.

Based on the initial pressure and temperature of the test gas, as well as the measured or estimated incident shock wave velocity, the app calculates the shocked and reflected gas conditions using the conservation equations of mass, energy, and momentum, known as the Rankine-Hugoniot relations [@anderson2021]. To achieve this, it employs an iterative procedure to determine the post-shock gas density using equations of state for air in thermodynamic equilibrium, established by @srini87. These equations of state are also available in Java, as outlined in @java2025.

Additionally, the application can estimate the free-stream flow conditions at the nozzle exit, assuming an isentropic process. The user can provide either the nozzle area ratio or the Pitot pressure measured in the flow, and the app will compute parameters such as pressure, temperature, and Mach number at the exit.

The incident shock wave velocity can be directly input based on experimental data provided by the user or estimated from the driver gas conditions, incorporating an empirical correction factor. This allows the app to support both the analysis of completed tests and the prediction of expected conditions for future experiments.

With a simple and efficient interface, the *Shock Tube and Tunnel Calculator quickly provides essential information for interpreting shock tunnel experiments, facilitating both analysis and test planning.




# References
