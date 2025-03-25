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

The Shock Tube and Tunnel Calculator addresses this issue by providing a mobile, user-friendly tool that enables fast calculation of the necessary parameters for characterizing shock tube and tunnel experiments. The app focuses on real air equilibrium parameters to estimate shocked and reflected conditions based on the initial pressure, temperature, and shock wave speed. Additionally, when a nozzle is used, it estimates the free stream based on the throat-to-exit area ratio or measured Pitot pressure, assuming isentropic, non-reactive flow. This provides an accurate solution for real gas scenarios, making it a practical tool for quick diagnostics and offering greater accessibility in various experimental settings.

# Mathematics 

## Shock Tube

For simplicity, the methodology used neglects mass, momentum, or energy transfer between the walls of the shock tunnel and the air filling it. Thus, the changes in states predicted for the test gas are not explained by viscous, frictional, or conductive phenomena, and any evolution is justified by the interaction between the test gas, shock waves (typical in shock tubes), and isentropic expansions (common in nozzles).

### Shocked Condition

From the conservation equations of momentum, energy, and mass in the one-dimensional, steady, constant-area form for gas processed by a normal shock wave, defined as the Rankine-Hugoniot relations, the following relationships are obtained:

$$
p_2 = p_1 + r_1 u_s^2 \left( 1 - \frac{r_1}{r_2} \right) \tag{1}
$$

$$
h_2 = h_1 + \frac{u_s^2}{2} \left[ 1 - \left( \frac{r_1}{r_2} \right)^2 \right] \tag{2}
$$

$$
u_2 = u_s \left( 1 - \frac{r_1}{r_2} \right) \tag{3}
$$

$$
r_2 = r_2(h_2, p_2) \tag{4}
$$

Where the subscript **s** refers to the primary shock wave, and the subscripts **1** and **2** refer to the initial and shocked conditions of the test gas. The equalities are arranged to adopt the shock wave as the inertial reference and express the dependence of the conservation equations on the density ratio between the initial and shocked states.

Although the initial condition of the test gas is known, along with the velocity of the incident shock wave (a commonly measured parameter), the lack of information about the shocked gas prevents direct application of the above equations, so an iterative approach is adopted.

Initially, the density of the shocked region is guessed, allowing the calculation of **p2** and **h2**. Then, the density **r2** is calculated using an equation of state that relates density, pressure, and specific enthalpy, or more precisely: **.** Once a new density is defined, the parameters **p2** and **h2** are recalculated, allowing the density to be recalculated again. This procedure is repeated until a convergence criterion is met. Finally, the velocity **u2** is calculated. As a direct consequence of using an equation of state, the procedure assumes that the air (test gas) evolves between equilibrium states.

All the equations of state used in the app are based on the work of [1], in which curve fitting was defined to calculate the thermodynamic properties of real air in equilibrium at temperatures up to 20,000 K. These curve fits can be found transcribed in Java in [2], the code used and adapted in the development of the app.
