/**
 * Copyright (c) 2012, Ben Fortuna
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  o Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  o Neither the name of Ben Fortuna nor the names of any other contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.fortuna.ical4j.connector.dav;

import net.fortuna.ical4j.connector.FailedOperationException;
import net.fortuna.ical4j.connector.ObjectStoreException;
import net.fortuna.ical4j.connector.dav.method.*;
import net.fortuna.ical4j.connector.dav.property.CSDavPropertyName;
import net.fortuna.ical4j.connector.dav.request.CalendarQuery;
import net.fortuna.ical4j.connector.dav.response.*;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.property.Attendee;
import net.fortuna.ical4j.model.property.Organizer;
import net.fortuna.ical4j.vcard.VCard;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicHeader;
import org.apache.jackrabbit.webdav.*;
import org.apache.jackrabbit.webdav.client.methods.*;
import org.apache.jackrabbit.webdav.property.DavPropertyName;
import org.apache.jackrabbit.webdav.property.DavPropertyNameSet;
import org.apache.jackrabbit.webdav.property.DavPropertySet;
import org.apache.jackrabbit.webdav.property.PropEntry;
import org.apache.jackrabbit.webdav.version.report.ReportInfo;
import org.apache.jackrabbit.webdav.version.report.ReportType;
import org.jetbrains.annotations.NotNull;
import org.jooq.lambda.Unchecked;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultDavClient implements CalDavSupport, CardDavSupport {

	/**
	 * The HTTP client configuration.
	 */
	private final HttpHost hostConfiguration;

	private final DavLocatorFactory locatorFactory;

	private final DavClientConfiguration clientConfiguration;

	/**
	 * The underlying HTTP client.
	 */
	protected HttpClient httpClient;

	protected HttpClientContext httpClientContext;

	private Header bearerAuth;

	/**
	 *
	 * @param url
	 * @deprecated use {@link DefaultDavClient#DefaultDavClient(URL, DavLocatorFactory, DavClientConfiguration)}
	 */
	@Deprecated
	public DefaultDavClient(URL url, PathResolver pathResolver) {
		this(url, pathResolver, new DavClientConfiguration());
	}

	/**
	 *
	 * @param url
	 * @deprecated use {@link DefaultDavClient#DefaultDavClient(URL, DavLocatorFactory, DavClientConfiguration)}
	 */
	@Deprecated
	public DefaultDavClient(URL url, PathResolver pathResolver, DavClientConfiguration clientConfiguration) {
		this(url, new CalDavLocatorFactory(pathResolver), clientConfiguration);
	}

	/**
	 * Create a disconnected DAV client instance.
	 */
	public DefaultDavClient(@NotNull String href, DavLocatorFactory locatorFactory, DavClientConfiguration clientConfiguration) throws MalformedURLException {
		this(URI.create(href).toURL(), locatorFactory, clientConfiguration);
	}

	/**
	 * Create a disconnected DAV client instance.
	 */
	public DefaultDavClient(@NotNull URL href, DavLocatorFactory locatorFactory,
							DavClientConfiguration clientConfiguration) {

		this.hostConfiguration = new HttpHost(href.getHost(), href.getPort(), href.getProtocol());
		this.locatorFactory = locatorFactory;
		this.clientConfiguration = clientConfiguration;
	}

	void begin() {
		httpClient = HttpClients.createDefault();
	}

	public List<SupportedFeature> begin(String bearerAuth) throws IOException, FailedOperationException {
		this.bearerAuth = new BasicHeader("Authorization", "Bearer " + bearerAuth);
		return getSupportedFeatures();
	}

	public List<SupportedFeature> begin(String username, char[] password) throws IOException, FailedOperationException {
		Credentials credentials = new UsernamePasswordCredentials(username, new String(password));

		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(new AuthScope(hostConfiguration), credentials);
		begin(credentialsProvider);

		return getSupportedFeatures();
	}

	void begin(CredentialsProvider credentialsProvider) {
		HttpClientBuilder builder = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider);
		if (clientConfiguration.isFollowRedirects()) {
			builder.setRedirectStrategy(new LaxRedirectStrategy());
		}
		httpClient = builder.build();
		httpClientContext = HttpClientContext.create();

		if (clientConfiguration.isPreemptiveAuth()) {
			AuthCache authCache = new BasicAuthCache();
			authCache.put(hostConfiguration, new BasicScheme());
			httpClientContext.setAuthCache(authCache);
		}
	}

	public List<SupportedFeature> getSupportedFeatures() throws IOException, FailedOperationException {
		DavPropertyNameSet props = new DavPropertyNameSet();
		props.add(DavPropertyName.RESOURCETYPE);
		props.add(CSDavPropertyName.CTAG);
		DavPropertyName owner = DavPropertyName.create(DavPropertyName.XML_OWNER, DavConstants.NAMESPACE);
		props.add(owner);

		DavResourceLocator resourceLocator = locatorFactory.createResourceLocator(null, hostConfiguration.toURI());
		HttpPropfind aGet = new HttpPropfind(resourceLocator.getRepositoryPath(), DavConstants.PROPFIND_BY_PROPERTY, props, 0);
		if (bearerAuth != null) {
			aGet.addHeader(bearerAuth);
		}

		RequestConfig.Builder builder = aGet.getConfig() == null ? RequestConfig.custom() : RequestConfig.copy(aGet.getConfig());
		builder.setAuthenticationEnabled(true);
		// Added to support iCal Server, who don't support Basic auth at all,
		// only Kerberos and Digest
		List<String> authPrefs = new ArrayList<String>(2);
		authPrefs.add(AuthSchemes.DIGEST);
		authPrefs.add(AuthSchemes.BASIC);
		builder.setTargetPreferredAuthSchemes(authPrefs);

		RequestConfig config = builder.build();
		aGet.setConfig(config);
		return execute(aGet, new GetSupportedFeatures());
	}

	@Override
	public void mkCalendar(String path, DavPropertySet properties) throws IOException, ObjectStoreException, DavException {
		MkCalendar mkCalendarMethod = new MkCalendar(path, properties);
		HttpResponse httpResponse = execute(mkCalendarMethod);
		if (!mkCalendarMethod.succeeded(httpResponse)) {
			throw new ObjectStoreException(httpResponse.getStatusLine().getStatusCode() + ": "
					+ httpResponse.getStatusLine().getReasonPhrase());
		}
	}

	@Override
	public void mkCol(String path) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public DavPropertySet propFind(String path, DavPropertyNameSet propertyNames) throws IOException {
		HttpPropfind aGet = new HttpPropfind(path, propertyNames, 0);

//		RequestConfig config = RequestConfig.custom().setAuthenticationEnabled(true).build();
//		aGet.setConfig(config);
		return execute(aGet, new GetResourceProperties());
	}

	public Map<String, DavPropertySet> propFindResources(String path, DavPropertyNameSet propertyNames,
														 ResourceType...resourceTypes) throws IOException {
		HttpPropfind aGet = new HttpPropfind(path, propertyNames, 0);

//		RequestConfig config = RequestConfig.custom().setAuthenticationEnabled(true).build();
//		aGet.setConfig(config);
		return execute(aGet, new GetCollections(resourceTypes));
	}

	@Override
	public DavPropertySet propFindType(String path, int type) throws IOException {
		HttpPropfind aGet = new HttpPropfind(path, type, 1);

//		RequestConfig config = RequestConfig.custom().setAuthenticationEnabled(true).build();
//		aGet.setConfig(config);
		return execute(aGet, new GetResourceProperties());
	}

	@Override
	public Map<String, DavPropertySet> report(String path, CalendarQuery query, ReportType reportType,
								   DavPropertyNameSet propertyNames) throws IOException, ParserConfigurationException {
		ReportInfo info = new ReportInfo(reportType, 1, propertyNames);
		info.setContentElement(query.build());

		HttpReport method = new HttpReport(path, info);
		return execute(method, new GetCollections());
	}

	@Override
	public <T> T report(String path, ReportInfo info, ResponseHandler<T> handler) throws IOException,
			ParserConfigurationException {

		HttpReport method = new HttpReport(path, info);
		return execute(method, handler);
	}

	@Override
	public <T> T get(String path, ResponseHandler<T> handler) throws IOException {
		HttpGet httpGet = new HttpGet(path);
		return execute(httpGet, handler);
	}

	@Override
	public <T> T head(String path, ResponseHandler<T> handler) throws IOException {
		HttpHead httpHead = new HttpHead(path);
		return execute(httpHead, handler);
	}

	@Override
	public void put(String path, Calendar calendar, String etag) throws IOException, FailedOperationException {
		PutCalendar httpPut = new PutCalendar(path);
		httpPut.setEtag(etag);
		httpPut.setCalendar(calendar);
		HttpResponse httpResponse = execute(httpPut);
		if (!httpPut.succeeded(httpResponse)) {
			throw new FailedOperationException(
					"Error creating calendar on server: " + httpResponse.getStatusLine());
		}
	}

	@Override
	public void put(String path, VCard card, String etag) throws IOException, FailedOperationException {
		PutVCard httpPut = new PutVCard(path);
		httpPut.setEtag(etag);
		httpPut.setVCard(card);
		HttpResponse httpResponse = execute(httpPut);
		if (!httpPut.succeeded(httpResponse)) {
			throw new FailedOperationException(
					"Error creating card on server: " + httpResponse.getStatusLine());
		}
	}

	@Override
	public void copy(String src, String dest) throws DavException, IOException {
		HttpCopy method = new HttpCopy(src, dest, true, false);
		execute(method, response -> {
			try {
				method.checkSuccess(response);
			} catch (DavException e) {
				Unchecked.throwChecked(e);
			}
			return true;
		});
	}

	@Override
	public void move(String src, String dest) throws IOException, DavException {
		HttpMove method = new HttpMove(src, dest, true);
		execute(method, response -> {
			try {
				method.checkSuccess(response);
			} catch (DavException e) {
				Unchecked.throwChecked(e);
			}
			return true;
		});
	}

	@Override
	public void delete(String path) throws IOException, DavException {
		HttpDelete method = new HttpDelete(path);
		execute(method, response -> {
			try {
				method.checkSuccess(response);
			} catch (DavException e) {
				Unchecked.throwChecked(e);
			}
			return true;
		});
	}

	public List<ScheduleResponse> freeBusy(String path, Calendar query, Organizer organizer) throws IOException {
		FreeBusy freeBusy = new FreeBusy(path, organizer);
		freeBusy.setQuery(query);
		return execute(freeBusy, new GetFreeBusyData());
	}

	@Override
	public MultiStatusResponse propPatch(String path, List<? extends PropEntry> changeList) throws IOException {
		HttpProppatch method = new HttpProppatch(path, changeList);
		return execute(method, response -> {
			try {
				method.checkSuccess(response);
				return method.getResponseBodyAsMultiStatus(response).getResponses()[0];
			} catch (DavException e) {
				Unchecked.throwChecked(e);
			}
			return null;
		});
	}

	@Override
	public void post() {

	}

	@Override
	public void lock(String path) {

	}

	@Override
	public void unlock(String path) {

	}

	public List<Attendee> findPrincipals(String href, PrincipalPropertySearchInfo info) throws IOException {
		PrincipalPropertySearch principalPropertySearch = new PrincipalPropertySearch(href, info);
		return execute(principalPropertySearch, new GetPrincipals());
	}

	private HttpResponse execute(BaseDavRequest method) throws IOException, DavException {
		HttpResponse response = httpClient.execute(hostConfiguration, method, httpClientContext);
		method.checkSuccess(response);
		return response;
	}

	private HttpResponse execute(HttpRequest method) throws IOException {
		return httpClient.execute(hostConfiguration, method, httpClientContext);
	}

	private <T> T execute(HttpRequest method, ResponseHandler<T> handler) throws IOException {
		return httpClient.execute(hostConfiguration, method, handler, httpClientContext);
	}
}
