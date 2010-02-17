﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:2.0.50727.4200
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Sun.Identity.Properties {
    using System;
    
    
    /// <summary>
    ///   A strongly-typed resource class, for looking up localized strings, etc.
    /// </summary>
    // This class was auto-generated by the StronglyTypedResourceBuilder
    // class via a tool like ResGen or Visual Studio.
    // To add or remove a member, edit your .ResX file then rerun ResGen
    // with the /str option, or rebuild your VS project.
    [global::System.CodeDom.Compiler.GeneratedCodeAttribute("System.Resources.Tools.StronglyTypedResourceBuilder", "2.0.0.0")]
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
    [global::System.Runtime.CompilerServices.CompilerGeneratedAttribute()]
    public class Resources {
        
        private static global::System.Resources.ResourceManager resourceMan;
        
        private static global::System.Globalization.CultureInfo resourceCulture;
        
        [global::System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1811:AvoidUncalledPrivateCode")]
        internal Resources() {
        }
        
        /// <summary>
        ///   Returns the cached ResourceManager instance used by this class.
        /// </summary>
        [global::System.ComponentModel.EditorBrowsableAttribute(global::System.ComponentModel.EditorBrowsableState.Advanced)]
        public static global::System.Resources.ResourceManager ResourceManager {
            get {
                if (object.ReferenceEquals(resourceMan, null)) {
                    global::System.Resources.ResourceManager temp = new global::System.Resources.ResourceManager("Sun.Identity.Properties.Resources", typeof(Resources).Assembly);
                    resourceMan = temp;
                }
                return resourceMan;
            }
        }
        
        /// <summary>
        ///   Overrides the current thread's CurrentUICulture property for all
        ///   resource lookups using this strongly typed resource class.
        /// </summary>
        [global::System.ComponentModel.EditorBrowsableAttribute(global::System.ComponentModel.EditorBrowsableState.Advanced)]
        public static global::System.Globalization.CultureInfo Culture {
            get {
                return resourceCulture;
            }
            set {
                resourceCulture = value;
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified artifact could not be convered from base64..
        /// </summary>
        public static string ArtifactFailedConversion {
            get {
                return ResourceManager.GetString("ArtifactFailedConversion", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified artifact did not have the correct unencoded length..
        /// </summary>
        public static string ArtifactInvalidLength {
            get {
                return ResourceManager.GetString("ArtifactInvalidLength", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified artifact was null or empty..
        /// </summary>
        public static string ArtifactNullOrEmpty {
            get {
                return ResourceManager.GetString("ArtifactNullOrEmpty", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to ArtifactResponse failed due to invalid InResponseTo ID..
        /// </summary>
        public static string ArtifactResolutionInvalidInResponseTo {
            get {
                return ResourceManager.GetString("ArtifactResolutionInvalidInResponseTo", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to An error occurred in resolving the SAML artifact..
        /// </summary>
        public static string ArtifactResolutionWebException {
            get {
                return ResourceManager.GetString("ArtifactResolutionWebException", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified SAML artifact response did not have the Response element..
        /// </summary>
        public static string ArtifactResponseMissingResponse {
            get {
                return ResourceManager.GetString("ArtifactResponseMissingResponse", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified SAML artifact response was null..
        /// </summary>
        public static string ArtifactResponseNullArgument {
            get {
                return ResourceManager.GetString("ArtifactResponseNullArgument", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified SAML artifact response threw an XML exception..
        /// </summary>
        public static string ArtifactResponseXmlException {
            get {
                return ResourceManager.GetString("ArtifactResponseXmlException", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to AssertionConsumerService not defined (binding nor index)..
        /// </summary>
        public static string AuthnRequestAssertionConsumerServiceNotDefined {
            get {
                return ResourceManager.GetString("AuthnRequestAssertionConsumerServiceNotDefined", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to AuthnRequest was null..
        /// </summary>
        public static string AuthnRequestIsNull {
            get {
                return ResourceManager.GetString("AuthnRequestIsNull", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to AuthnResponse failed validation due to an invalid condition related to the target audience..
        /// </summary>
        public static string AuthnResponseInvalidConditionAudience {
            get {
                return ResourceManager.GetString("AuthnResponseInvalidConditionAudience", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to AuthnResponse failed validation due to an invalid condition related to time..
        /// </summary>
        public static string AuthnResponseInvalidConditionTime {
            get {
                return ResourceManager.GetString("AuthnResponseInvalidConditionTime", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to AuthnResponse failed validation due to unknown InResponseTo ID..
        /// </summary>
        public static string AuthnResponseInvalidInResponseTo {
            get {
                return ResourceManager.GetString("AuthnResponseInvalidInResponseTo", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to AuthnResponse failed validation due to an invalid signature..
        /// </summary>
        public static string AuthnResponseInvalidSignature {
            get {
                return ResourceManager.GetString("AuthnResponseInvalidSignature", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to AuthnResponse failed validation due to the signature in the response not matching the signature in the IDP metadata..
        /// </summary>
        public static string AuthnResponseInvalidSignatureCertsDontMatch {
            get {
                return ResourceManager.GetString("AuthnResponseInvalidSignatureCertsDontMatch", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to AuthnResponse failed validation due to missing signature..
        /// </summary>
        public static string AuthnResponseInvalidSignatureMissing {
            get {
                return ResourceManager.GetString("AuthnResponseInvalidSignatureMissing", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to AuthnResponse failed validation due to missing signature on the ArtifactResponse element..
        /// </summary>
        public static string AuthnResponseInvalidSignatureMissingOnArtifactResponse {
            get {
                return ResourceManager.GetString("AuthnResponseInvalidSignatureMissingOnArtifactResponse", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to AuthnResponse failed validation due to missing signature on the Response element..
        /// </summary>
        public static string AuthnResponseInvalidSignatureMissingOnResponse {
            get {
                return ResourceManager.GetString("AuthnResponseInvalidSignatureMissingOnResponse", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to AuthnResponse failed validation due to invalid signature reference..
        /// </summary>
        public static string AuthnResponseInvalidSignatureReference {
            get {
                return ResourceManager.GetString("AuthnResponseInvalidSignatureReference", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified SAML response was null..
        /// </summary>
        public static string AuthnResponseNullArgument {
            get {
                return ResourceManager.GetString("AuthnResponseNullArgument", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified SAML response threw an XML exception..
        /// </summary>
        public static string AuthnResponseXmlException {
            get {
                return ResourceManager.GetString("AuthnResponseXmlException", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified home folder for the circle of trusts could not be found..
        /// </summary>
        public static string CircleOfTrustDirNotFound {
            get {
                return ResourceManager.GetString("CircleOfTrustDirNotFound", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to The circle of trust file could not be found..
        /// </summary>
        public static string CircleOfTrustFileNotFound {
            get {
                return ResourceManager.GetString("CircleOfTrustFileNotFound", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to An exception occurred in acquiring circle of trust information..
        /// </summary>
        public static string CircleOfTrustUnhandledException {
            get {
                return ResourceManager.GetString("CircleOfTrustUnhandledException", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified home folder for the identity provider could not be found..
        /// </summary>
        public static string IdentityProviderDirNotFound {
            get {
                return ResourceManager.GetString("IdentityProviderDirNotFound", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Metadata for the identity provider could not be found..
        /// </summary>
        public static string IdentityProviderFileNotFound {
            get {
                return ResourceManager.GetString("IdentityProviderFileNotFound", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified metadata for the identity provider threw an XML exception..
        /// </summary>
        public static string IdentityProviderXmlException {
            get {
                return ResourceManager.GetString("IdentityProviderXmlException", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Validation failed due to the IDP and SP not being in the same circle of trust..
        /// </summary>
        public static string InvalidIdpEntityIdNotInCircleOfTrust {
            get {
                return ResourceManager.GetString("InvalidIdpEntityIdNotInCircleOfTrust", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Validation failed due to an invalid InResponseTo ID..
        /// </summary>
        public static string InvalidInResponseTo {
            get {
                return ResourceManager.GetString("InvalidInResponseTo", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Validation failed due to an invalid Issuer..
        /// </summary>
        public static string InvalidIssuer {
            get {
                return ResourceManager.GetString("InvalidIssuer", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Validation failed due to an invalid StatusCode..
        /// </summary>
        public static string InvalidStatusCode {
            get {
                return ResourceManager.GetString("InvalidStatusCode", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Identity Provider was null..
        /// </summary>
        public static string LogoutRequestIdentityProviderIsNull {
            get {
                return ResourceManager.GetString("LogoutRequestIdentityProviderIsNull", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to LogoutRequest failed validation due to missing signature..
        /// </summary>
        public static string LogoutRequestInvalidSignatureMissing {
            get {
                return ResourceManager.GetString("LogoutRequestInvalidSignatureMissing", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to LogoutRequest was null..
        /// </summary>
        public static string LogoutRequestIsNull {
            get {
                return ResourceManager.GetString("LogoutRequestIsNull", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified SAML logout request was null..
        /// </summary>
        public static string LogoutRequestNullArgument {
            get {
                return ResourceManager.GetString("LogoutRequestNullArgument", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Service Provider was null..
        /// </summary>
        public static string LogoutRequestServiceProviderIsNull {
            get {
                return ResourceManager.GetString("LogoutRequestServiceProviderIsNull", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to SessionIndex was not defined..
        /// </summary>
        public static string LogoutRequestSessionIndexNotDefined {
            get {
                return ResourceManager.GetString("LogoutRequestSessionIndexNotDefined", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to SubjectNameId was not defined..
        /// </summary>
        public static string LogoutRequestSubjectNameIdNotDefined {
            get {
                return ResourceManager.GetString("LogoutRequestSubjectNameIdNotDefined", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to An error occurred in sending the LogoutRequest SOAP message..
        /// </summary>
        public static string LogoutRequestWebException {
            get {
                return ResourceManager.GetString("LogoutRequestWebException", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified SAML logout request threw an XML exception..
        /// </summary>
        public static string LogoutRequestXmlException {
            get {
                return ResourceManager.GetString("LogoutRequestXmlException", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Identity Provider was null..
        /// </summary>
        public static string LogoutResponseIdentityProviderIsNull {
            get {
                return ResourceManager.GetString("LogoutResponseIdentityProviderIsNull", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to LogoutResponse failed validation due to unknown InResponseTo ID..
        /// </summary>
        public static string LogoutResponseInvalidInResponseTo {
            get {
                return ResourceManager.GetString("LogoutResponseInvalidInResponseTo", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to LogoutResponse failed validatione due to invalid Issuer..
        /// </summary>
        public static string LogoutResponseInvalidIssuer {
            get {
                return ResourceManager.GetString("LogoutResponseInvalidIssuer", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to LogoutResponse failed validation due to an invalid status code..
        /// </summary>
        public static string LogoutResponseInvalidStatusCode {
            get {
                return ResourceManager.GetString("LogoutResponseInvalidStatusCode", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Logout Request was null..
        /// </summary>
        public static string LogoutResponseLogoutRequestIsNull {
            get {
                return ResourceManager.GetString("LogoutResponseLogoutRequestIsNull", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified SAML logout response was null..
        /// </summary>
        public static string LogoutResponseNullArgument {
            get {
                return ResourceManager.GetString("LogoutResponseNullArgument", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Service Provider was null..
        /// </summary>
        public static string LogoutResponseServiceProviderIsNull {
            get {
                return ResourceManager.GetString("LogoutResponseServiceProviderIsNull", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified SAML logout response threw an XML exception..
        /// </summary>
        public static string LogoutResponseXmlException {
            get {
                return ResourceManager.GetString("LogoutResponseXmlException", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Neither AuthnContextClassRef or AuthnContextDeclRef defined..
        /// </summary>
        public static string RequestedAuthnContextClassRefOrDeclRefNotDefined {
            get {
                return ResourceManager.GetString("RequestedAuthnContextClassRefOrDeclRefNotDefined", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to An invalid comparison was specified for the RequestedAuthnContext..
        /// </summary>
        public static string RequestedAuthnContextInvalidComparison {
            get {
                return ResourceManager.GetString("RequestedAuthnContextInvalidComparison", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified home folder for the service provider could not be found..
        /// </summary>
        public static string ServiceProviderDirNotFound {
            get {
                return ResourceManager.GetString("ServiceProviderDirNotFound", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Metadata for the service provider could not be found..
        /// </summary>
        public static string ServiceProviderFileNotFound {
            get {
                return ResourceManager.GetString("ServiceProviderFileNotFound", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified home folder could not be found..
        /// </summary>
        public static string ServiceProviderUtilityHomeFolderNotFound {
            get {
                return ResourceManager.GetString("ServiceProviderUtilityHomeFolderNotFound", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified IDP EntityID was not found in the available metadata..
        /// </summary>
        public static string ServiceProviderUtilityIdentityProviderNotFound {
            get {
                return ResourceManager.GetString("ServiceProviderUtilityIdentityProviderNotFound", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to No identity provider metadata files were found in the home folder..
        /// </summary>
        public static string ServiceProviderUtilityIdentityProvidersNotFound {
            get {
                return ResourceManager.GetString("ServiceProviderUtilityIdentityProvidersNotFound", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Artifact Resolution Service Location not defined for the specified IDP..
        /// </summary>
        public static string ServiceProviderUtilityIdpArtifactResSvcLocNotDefined {
            get {
                return ResourceManager.GetString("ServiceProviderUtilityIdpArtifactResSvcLocNotDefined", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Could not determine IDP from the obtained artifact..
        /// </summary>
        public static string ServiceProviderUtilityIdpNotDeterminedFromArtifact {
            get {
                return ResourceManager.GetString("ServiceProviderUtilityIdpNotDeterminedFromArtifact", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Could not determine IDP from the logout request..
        /// </summary>
        public static string ServiceProviderUtilityIdpNotDeterminedFromLogoutRequest {
            get {
                return ResourceManager.GetString("ServiceProviderUtilityIdpNotDeterminedFromLogoutRequest", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Single Logout Service Location not defined for the specified IDP..
        /// </summary>
        public static string ServiceProviderUtilityIdpSingleLogoutSvcLocNotDefined {
            get {
                return ResourceManager.GetString("ServiceProviderUtilityIdpSingleLogoutSvcLocNotDefined", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Single Logout Service Response Location not defined for the specified IDP..
        /// </summary>
        public static string ServiceProviderUtilityIdpSingleLogoutSvcResLocNotDefined {
            get {
                return ResourceManager.GetString("ServiceProviderUtilityIdpSingleLogoutSvcResLocNotDefined", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Single Sign On Service Location not defined for the specified IDP..
        /// </summary>
        public static string ServiceProviderUtilityIdpSingleSignOnSvcLocNotDefined {
            get {
                return ResourceManager.GetString("ServiceProviderUtilityIdpSingleSignOnSvcLocNotDefined", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to LogoutRequest was null..
        /// </summary>
        public static string ServiceProviderUtilityLogoutRequestIsNull {
            get {
                return ResourceManager.GetString("ServiceProviderUtilityLogoutRequestIsNull", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to LogoutResponse was null..
        /// </summary>
        public static string ServiceProviderUtilityLogoutResponseIsNull {
            get {
                return ResourceManager.GetString("ServiceProviderUtilityLogoutResponseIsNull", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to No SAML Request was received..
        /// </summary>
        public static string ServiceProviderUtilityNoSamlRequestReceived {
            get {
                return ResourceManager.GetString("ServiceProviderUtilityNoSamlRequestReceived", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to No SAML Response was received..
        /// </summary>
        public static string ServiceProviderUtilityNoSamlResponseReceived {
            get {
                return ResourceManager.GetString("ServiceProviderUtilityNoSamlResponseReceived", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified logout binding is not supported..
        /// </summary>
        public static string ServiceProviderUtilityUnsupportedLogoutBinding {
            get {
                return ResourceManager.GetString("ServiceProviderUtilityUnsupportedLogoutBinding", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to No circle of trust configuration files were found in the home folder..
        /// </summary>
        public static string ServiceProviderUtiltyCircleOfTrustsNotFound {
            get {
                return ResourceManager.GetString("ServiceProviderUtiltyCircleOfTrustsNotFound", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Specified metadata for the service provider threw an XML exception..
        /// </summary>
        public static string ServiceProviderXmlException {
            get {
                return ResourceManager.GetString("ServiceProviderXmlException", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Certifcate not provided for checking query string signature..
        /// </summary>
        public static string SignedQueryStringCertIsNull {
            get {
                return ResourceManager.GetString("SignedQueryStringCertIsNull", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Query string not provided for checking signature..
        /// </summary>
        public static string SignedQueryStringIsNull {
            get {
                return ResourceManager.GetString("SignedQueryStringIsNull", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Query string did not have the SigAlg parameter used for query string validation..
        /// </summary>
        public static string SignedQueryStringMissingSigAlg {
            get {
                return ResourceManager.GetString("SignedQueryStringMissingSigAlg", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Query string did not have the Signature parameter used for query string validation..
        /// </summary>
        public static string SignedQueryStringMissingSignature {
            get {
                return ResourceManager.GetString("SignedQueryStringMissingSignature", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Signed query string specified an unsupported signature algorithm..
        /// </summary>
        public static string SignedQueryStringUnsupportedSigAlg {
            get {
                return ResourceManager.GetString("SignedQueryStringUnsupportedSigAlg", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Signed query string did not have the correct signature..
        /// </summary>
        public static string SignedQueryStringVerifyDataFailed {
            get {
                return ResourceManager.GetString("SignedQueryStringVerifyDataFailed", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Signed XML did not have the correct signature..
        /// </summary>
        public static string SignedXmlCheckSignatureFailed {
            get {
                return ResourceManager.GetString("SignedXmlCheckSignatureFailed", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Signed XML did not have the desired reference ID..
        /// </summary>
        public static string SignedXmlInvalidReference {
            get {
                return ResourceManager.GetString("SignedXmlInvalidReference", resourceCulture);
            }
        }
    }
}
