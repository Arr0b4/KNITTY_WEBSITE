import { Link } from "react-router-dom";
import "../../../styles/Policy.css";

export default function TermsOfService() {
	return (
		<section className="policy" aria-labelledby="termsTitle">
			<div className="policy__content">
				<header className="policy__header">
					<h1 id="termsTitle" className="policy__title">
						Terms of Service
					</h1>
				</header>

				<section className="policy__section" aria-labelledby="termsIntro">
					<h2 id="termsIntro" className="policy__h2">
						Introduction
					</h2>

					<p className="policy__text">
						These Terms of Service (“Terms”) govern your access to and use of{" "}
						<span className="policy__mono">knitty.com</span> (the
						“Site”), including browsing, creating an account, and purchasing
						products from Knitty.
					</p>

					<p className="policy__text">
						By accessing or using the Site, you agree to these Terms. If you do
						not agree, please do not use the Site.
					</p>
				</section>

				<section className="policy__section" aria-labelledby="termsChanges">
					<h2 id="termsChanges" className="policy__h2">
						Changes to These Terms
					</h2>

					<p className="policy__text">
						We may update these Terms from time to time to reflect changes to
						the Site, our services, business practices, or legal requirements.
						The updated version will be posted on the Site.
					</p>
				</section>

				<section className="policy__section" aria-labelledby="termsProducts">
					<h2 id="termsProducts" className="policy__h2">
						Product Information
					</h2>

					<p className="policy__text">
						We aim to provide accurate product descriptions, photographs,
						colors, measurements, pricing, and availability. However, minor
						differences may occur due to screen settings, photography, handmade
						production, yarn characteristics, and natural variations in
						materials.
					</p>

				</section>


				<section className="policy__section" aria-labelledby="termsOrders">
					<h2 id="termsOrders" className="policy__h2">
						Orders &amp; Payments
					</h2>

					<p className="policy__text">
						When placing an order, you confirm that the information you provide
						is accurate, complete, and current and that you are authorized to
						use the selected payment method.
					</p>

					<p className="policy__text">
						Submission of an order does not prevent us from refusing or
						cancelling an order where reasonably necessary, including in cases
						of suspected fraud, payment issues, incorrect pricing, product
						unavailability, or other legitimate reasons.
					</p>
				</section>


				<section className="policy__section" aria-labelledby="termsShipping">
					<h2 id="termsShipping" className="policy__h2">
						Shipping
					</h2>

					<p className="policy__text">
						Processing, production, delivery, tracking, customs, and address
						terms are described in our{" "}
						<Link className="policy__link" to="/policies/shipping-policy">
							Shipping Policy
						</Link>
						.
					</p>
				</section>

				<section className="policy__section" aria-labelledby="termsIP">
					<h2 id="termsIP" className="policy__h2">
						Intellectual Property
					</h2>

					<p className="policy__text">
						Unless otherwise stated, content on the Site, including logos,
						branding, text, product photographs, graphics, visual materials, and
						original website content, is owned by Knitty or used with permission
						and is protected by applicable intellectual property laws.
					</p>

					<p className="policy__text">
						You may not reproduce, distribute, modify, publish, commercially
						exploit, or use protected Site content without authorization, except
						as permitted by applicable law.
					</p>
				</section>

				<section className="policy__section" aria-labelledby="termsUser">
					<h2 id="termsUser" className="policy__h2">
						User Obligations
					</h2>

					<p className="policy__text">
						You agree not to misuse the Site, attempt unauthorized access,
						interfere with security or operation, introduce malicious code,
						conduct unauthorized automated scraping, commit fraud, or use the
						Site in violation of applicable law.
					</p>
				</section>

				<section className="policy__section" aria-labelledby="termsLaw">
					<h2 id="termsLaw" className="policy__h2">
						Applicable Law
					</h2>

					<p className="policy__text">
						These Terms are governed by applicable laws and are subject to any
						mandatory consumer protection rights that apply to a customer and
						cannot lawfully be waived.
					</p>
				</section>

				<section className="policy__section" aria-labelledby="termsContact">
					<h2 id="termsContact" className="policy__h2">
						Contact
					</h2>

					<p className="policy__text">
						Email:{" "}
						<a
							className="policy__link"
							href="mailto:support@knitty.com"
						>
							support@knitty.com
						</a>
					</p>
				</section>
			</div>
		</section>
	);
}
