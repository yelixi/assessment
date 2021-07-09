package com.sacc.assessment.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

/**
 * Created by 林夕
 * Date 2021/6/15 10:17
 */
@PreAuthorize("hasRole('ISSUER')")
@Controller
public class IssuerController {
}
