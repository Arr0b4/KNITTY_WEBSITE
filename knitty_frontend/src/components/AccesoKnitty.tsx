import { useState, type FormEvent } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { acceder, registrar } from '../services/authKnitty';
import loginImage from '../assets/images/Login.webp';
import registerImage from '../assets/images/Register.webp';
import '../../styles/Authorization.css';
import '../../styles/Register.css';

type Props = { modo: 'registro' | 'login' };

export default function AccesoKnitty({ modo }: Props) {
  const navigate = useNavigate();
  const [nombre, setNombre] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmacion, setConfirmacion] = useState('');
  const [mensaje, setMensaje] = useState('');
  const [enviando, setEnviando] = useState(false);

  async function enviar(e: FormEvent<HTMLFormElement>) {
    e.preventDefault();
    setMensaje('');
    if (modo === 'registro' && nombre.trim().length < 2) { setMensaje('El nombre debe tener al menos 2 caracteres.'); return; }
    if (password.length < 8) { setMensaje('La contraseña debe tener al menos 8 caracteres.'); return; }
    if (modo === 'registro' && password !== confirmacion) { setMensaje('Las contraseñas no coinciden.'); return; }
    setEnviando(true);
    try {
      if (modo === 'registro') await registrar(nombre.trim(), email.trim(), password);
      else await acceder(email.trim(), password);
      navigate('/zona-usuario', { replace: true, state: { mensaje: modo === 'registro' ? 'Tu cuenta se ha creado correctamente.' : 'Sesión iniciada correctamente.' } });
    } catch (error: unknown) {
      setMensaje(error instanceof Error ? error.message : 'No se pudo completar la operación.');
    } finally { setEnviando(false); }
  }

  return (
    <section className={modo === 'registro' ? 'register' : 'authorization'}>
      <div className={modo === 'registro' ? 'register__layout' : 'authorization__layout'}>
        <div className={modo === 'registro' ? 'register__left' : 'authorization__left'}>
          <div className={modo === 'registro' ? 'register__content' : 'authorization__content'}>
            <h1 className={modo === 'registro' ? 'register__title' : 'authorization__title'}>
              {modo === 'registro' ? 'Create your account' : 'Welcome back to KNITTY'}
            </h1>
            {modo === 'login' && <p className="authorization__subtitle">Enter your details to access your account.</p>}
            <form className={modo === 'registro' ? 'register__form' : 'authorization__form'} onSubmit={(e) => { void enviar(e); }}>
              {modo === 'registro' && <label className="register__field"><span className="register__label">Full name</span><input className="register__input" type="text" autoComplete="name" value={nombre} minLength={2} maxLength={100} required onChange={e => setNombre(e.target.value)} /></label>}
              <label className={modo === 'registro' ? 'register__field' : 'authorization__field'}><span className={modo === 'registro' ? 'register__label' : 'authorization__label'}>Email</span><input className={modo === 'registro' ? 'register__input' : 'authorization__input'} type="email" autoComplete="email" value={email} maxLength={150} required onChange={e => setEmail(e.target.value)} /></label>
              <label className={modo === 'registro' ? 'register__field' : 'authorization__field'}><span className={modo === 'registro' ? 'register__label' : 'authorization__label'}>Password</span><input className={modo === 'registro' ? 'register__input' : 'authorization__input'} type="password" autoComplete={modo === 'registro' ? 'new-password' : 'current-password'} value={password} minLength={8} maxLength={72} required onChange={e => setPassword(e.target.value)} /></label>
              {modo === 'registro' && <label className="register__field"><span className="register__label">Confirm password</span><input className="register__input" type="password" autoComplete="new-password" value={confirmacion} minLength={8} required onChange={e => setConfirmacion(e.target.value)} /></label>}
              {mensaje && <p className={modo === 'registro' ? 'register__serverError' : 'authorization__message authorization__message--server'} role="alert">{mensaje}</p>}
              <button className={modo === 'registro' ? 'register__cta register__cta--primary' : 'authorization__cta authorization__cta--primary'} type="submit" disabled={enviando}>{enviando ? 'PLEASE WAIT…' : modo === 'registro' ? 'SIGN UP' : 'LOG IN'}</button>
            </form>
            <p>{modo === 'registro' ? 'Already have an account?' : "Don't have an account yet?"} <Link to={modo === 'registro' ? '/login' : '/register'}>{modo === 'registro' ? 'Log in' : 'Sign up'}</Link></p>
            <Link to="/">Back to home</Link>
          </div>
        </div>
        <div className={modo === 'registro' ? 'register__right' : 'authorization__right'}>
          <div className={modo === 'registro' ? 'register__media' : 'authorization__media'}>
            <img className={modo === 'registro' ? 'register__image' : 'authorization__image'} src={modo === 'registro' ? registerImage : loginImage} alt="" aria-hidden="true" />
          </div>
        </div>
      </div>
    </section>
  );
}
